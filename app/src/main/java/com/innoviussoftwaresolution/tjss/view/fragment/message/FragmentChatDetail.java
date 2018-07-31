package com.innoviussoftwaresolution.tjss.view.fragment.message;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugsnag.android.Bugsnag;
import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.TjssApplication;
import com.innoviussoftwaresolution.tjss.model.internal.Chat;
import com.innoviussoftwaresolution.tjss.model.internal.ChatInfo;
import com.innoviussoftwaresolution.tjss.model.internal.ChatMessage;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.request.UploadChatFileRequest;
import com.innoviussoftwaresolution.tjss.model.network.request.helper.RequestHelper;
import com.innoviussoftwaresolution.tjss.model.network.request.serviceinterface.TjssNetworkInterface;
import com.innoviussoftwaresolution.tjss.model.network.response.ChatFileUploadResponse;
import com.innoviussoftwaresolution.tjss.service.ChatConstants;
import com.innoviussoftwaresolution.tjss.service.ChatListenerService;
import com.innoviussoftwaresolution.tjss.utils.FileUtil;
import com.innoviussoftwaresolution.tjss.utils.M;
import com.innoviussoftwaresolution.tjss.utils.PreferenceHelper;
import com.innoviussoftwaresolution.tjss.view.activity.GroupMembersListingActivity;
import com.innoviussoftwaresolution.tjss.view.adapter.listadapter.ChatDetailAdapter;
import com.innoviussoftwaresolution.tjss.view.fragment.generalalerts.PermissionsDetailListDialog;
import com.innoviussoftwaresolution.tjss.view.fragment.message.alert.PickerDialog;
import com.innoviussoftwaresolution.tjss.view.viewutils.Permissions;
import com.innoviussoftwaresolution.tjss.viewmodel.ChatDetailViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;

/**
 * @author Sony Raj on 24-07-17.
 */

public class FragmentChatDetail extends MessageFragmentBase implements
        PermissionsDetailListDialog.PermissionsDialogCallback,
        ChatDetailAdapter.OnItemDeletedListener, PickerDialog.PickerSelectionListener {


    public static final String USERS = "users";
    public static final String GROUP_NAME = "groupName";

    public static final String TAG = "chatDetail";
    public static final String NEW = "new";

    private static final int SELECT_IMAGE_REQUEST_CODE = 1;
    private static final int SELECT_VIDEO_REQUEST_CODE = 2;
    private static final int SELECT_AUDIO_REQUEST_CODE = 3;

    private static final int CAPTURE_IMAGE_REQUEST_CODE = 4;
    private static final int CAPTURE_VIDEO_REQUEST_CODE = 5;
    private static final int RECORD_AUDIO_REQUEST_CODE = 6;

    private HashMap<String, ChatMessage> newMessages = new HashMap<>();

    @BindView(R.id.lstChatMessages)
    RecyclerView mLstChatMessages;
    @BindView(R.id.txtChatMessage)
    AppCompatEditText mTxtChatMessage;
    Unbinder unbinder;
    private Intent readAllIntent;
    private Handler mScrollToBottomHandler;
    private Runnable mScrollToBottomRunnable;

    private ChatDetailViewModel mViewModel;

    @Override
    public void onStart() {
        super.onStart();
        ((TjssApplication) getActivity().getApplication()).setCurrentRoom(mGroupName);
    }

    @Override
    public void onStop() {
        super.onStop();
        ((TjssApplication) getActivity().getApplication()).setCurrentRoom("");
    }

    private String mUsers = null, mGroupName = null, isNew = null;

    private String mUserName, mUserImage, mUserId;
    private Observer<Chat> mOldMessagesObserver
            = new Observer<Chat>() {
        @Override
        public void onChanged(@Nullable final Chat chat) {
            if (chat == null) return;

            if (chat.title != null && !chat.title.equals("")) {
                try {
                    setTitle(chat.title);
                } catch (Exception e) {
                    M.log(e.getMessage());
                }
            }
            List<ChatMessage> chatMessages = chat.chatHistory;

            if (mLstChatMessages.getAdapter() == null) {
                mLstChatMessages.setAdapter(new ChatDetailAdapter(getActivity(), mLstChatMessages));
                mLstChatMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            if (chatMessages == null || chatMessages.size() <= 0) {
                ((ChatDetailAdapter) mLstChatMessages.getAdapter()).setMessages(new ArrayList<ChatMessage>(0));
            } else {
                ((ChatDetailAdapter) mLstChatMessages.getAdapter()).setMessages(chatMessages);
            }

            if (chatMessages == null || chatMessages.size() <= 0) return;

            scrollToBottom();
            sendReadAll(chat.groupId);
        }
    };
    private String mCurrentPhotoPath = null;

    public static FragmentChatDetail newInstance(String users, String groupName, String isNew) {
        Bundle args = new Bundle();
        args.putString(USERS, users);
        args.putString(GROUP_NAME, groupName);
        args.putString(NEW, isNew);
        FragmentChatDetail fragment = new FragmentChatDetail();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendReadAll(String groupId) {
        if (readAllIntent == null) {
            readAllIntent = new Intent();
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put(ChatConstants.MESSAGE_GROUP_ID, groupId);
            } catch (JSONException e) {
                Bugsnag.notify(new RuntimeException(e));
                e.printStackTrace();
            }
            readAllIntent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
            readAllIntent.putExtra(ChatListenerService.EXTRA, ChatListenerService.READ_ALL_MESSAGE);
            readAllIntent.putExtra(ChatListenerService.EXTRA_PAYLOAD, jsonObject.toString());
        }
        LocalBroadcastManager.getInstance(getActivity())
                .sendBroadcast(readAllIntent);
    }

    private void scrollToBottom() {
        if (mLstChatMessages == null) return;
        if (mScrollToBottomHandler == null) mScrollToBottomHandler = new Handler();
        if (mScrollToBottomRunnable == null)
            mScrollToBottomRunnable = new Runnable() {
                @Override
                public void run() {
                    mLstChatMessages.scrollToPosition(mLstChatMessages.getAdapter().getItemCount() - 1);
                }
            };
        mScrollToBottomHandler.postDelayed(mScrollToBottomRunnable, 150);
    }

    @Override
    public void onBaseCreate(Bundle SavedInstanceState) {
        Bundle args = getArguments();
        if (args == null) return;
        mUsers = args.getString(USERS, null);
        mGroupName = args.getString(GROUP_NAME, null);
        isNew = args.getString(NEW, "");
        Bugsnag.init(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        mViewModel = ViewModelProviders.of(this,
                new ChatDetailViewModel.ChatDetailViewModelFactory(getActivity().getApplication()))
                .get(ChatDetailViewModel.class);
        mViewModel.getOldMessagesData().observe(this, mOldMessagesObserver);

        ChatDetailAdapter chatDetailAdapter = new ChatDetailAdapter(getActivity(), mLstChatMessages);
        chatDetailAdapter.setDeleteListener(this);
        mLstChatMessages.setAdapter(chatDetailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

        mLstChatMessages.setLayoutManager(linearLayoutManager);

        mUserName = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_FIRST_NAME, "");
        mUserId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, "");
        mUserImage = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PROFILE_IMAGE, "");

        if (mUsers != null) {
            String circleId = PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_PRIMARY_CIRCLE, "");
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("circleId", circleId);
                jsonObject.put("members", mUsers);
                jsonObject.put("groupName", mGroupName == null ? "" : mGroupName);
                jsonObject.put("status", isNew);
                Intent intent = new Intent();
                intent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
                intent.putExtra(ChatListenerService.EXTRA, ChatListenerService.CREATE_ROOM);
                intent.putExtra(ChatListenerService.EXTRA_PAYLOAD, jsonObject.toString());
                LocalBroadcastManager.getInstance(getActivity())
                        .sendBroadcast(intent);
            } catch (Exception e) {
                Bugsnag.notify(new RuntimeException(e));
                M.log(e.getMessage());
            }
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnSendMessage)
    public void onSendClicked() {
        if (TextUtils.isEmpty(mTxtChatMessage.getText())) return;
        String newMessage = mTxtChatMessage.getText().toString().trim();
        if (TextUtils.isEmpty(newMessage)) return;
        JSONObject jsonObject = new JSONObject();
        try {
            String key = System.currentTimeMillis() + "-" + mViewModel.getGroupId() + "-" + mLstChatMessages.getAdapter().getItemCount();
            jsonObject.put(ChatConstants.MESSAGE_CONTENT, newMessage);
            jsonObject.put(ChatConstants.MESSAGE_FROM_ID, mUserId);
            jsonObject.put(ChatConstants.MESSAGE_GROUP_ID, mViewModel.getGroupId());
            jsonObject.put(ChatConstants.MESSAGE_SENDER_NAME, mUserName);
            jsonObject.put(ChatConstants.MESSAGE_SENDER_IMAGE, mUserImage);
            jsonObject.put(ChatConstants.THUMBNAIL, "");
            jsonObject.put(ChatConstants.MESSAGE_TYPE, "1");
            jsonObject.put(ChatConstants.KEY, key);

            Intent intent = new Intent();
            intent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
            intent.putExtra(ChatConstants.EXTRA, ChatConstants.SEND_MESSAGE);
            intent.putExtra(ChatConstants.EXTRA_PAYLOAD, jsonObject.toString());
            LocalBroadcastManager.getInstance(getActivity())
                    .sendBroadcast(intent);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.content = newMessage;
            chatMessage.fromId = mUserId;
            chatMessage.groupId = mViewModel.getGroupId();
            chatMessage.senderImage = mUserImage;
            chatMessage.senderName = mUserName;
            chatMessage.messageType = "1";
            chatMessage.key = key;
            newMessages.put(key, chatMessage);

            if (mLstChatMessages.getAdapter() == null) {
                ChatDetailAdapter chatDetailAdapter = new ChatDetailAdapter(getActivity(), mLstChatMessages);
                chatDetailAdapter.setDeleteListener(this);
                mLstChatMessages.setAdapter(new ChatDetailAdapter(getActivity(), mLstChatMessages));
                mLstChatMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            ((ChatDetailAdapter) mLstChatMessages.getAdapter()).addMessage(chatMessage);
            mTxtChatMessage.setText("");
            scrollToBottom();
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btnAttachment)
    public void onAttachmentClicked() {
        if (!havePermission(Permissions.READ_EXTERNAL_STORAGE, Permissions.CAMERA, Permissions.RECORD_AUDIO)) {
            if (shouldShowPermissionReationale(Permissions.READ_EXTERNAL_STORAGE, Permissions.CAMERA, Permissions.RECORD_AUDIO) != null) {
                ArrayList<String> permissionDetails = new ArrayList<>(1);
                permissionDetails.add("Request to read External Storage-TJSS will need your permission to read external storage to select images/video");
                permissionDetails.add("Request to user camera-TJSS will need your permission to use camera to capture images/video");
                permissionDetails.add("Request to use mic-TJSS will need your permission to use mic to record audio clip to send voice messages");
                PermissionsDetailListDialog dialog = PermissionsDetailListDialog.newInstance(permissionDetails);
                dialog.setCallback(this);
                dialog.setCancelable(false);
                dialog.show(getChildFragmentManager(), "PermissionsDialog");
            } else {
                requestPermission(Permissions.CAMERA_AND_STORAGE_REQUEST_CODE + RECORD_AUDIO_REQUEST_CODE, Permissions.READ_EXTERNAL_STORAGE, Permissions.CAMERA, Permissions.RECORD_AUDIO);
            }
        } else {
            showSelectionDialog();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mToolbar != null)
            mToolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mViewModel.getOldMessagesData().getValue() != null) {
                        Intent detail = new Intent(getActivity(), GroupMembersListingActivity.class);
                        detail.putExtra(GroupMembersListingActivity.GROUP_ID, mViewModel.getOldMessagesData().getValue().groupId);
                        detail.putExtra(GroupMembersListingActivity.GROUP_NAME, mGroupName);
                        startActivity(detail);
                    }
                }
            });
    }

    @Override
    public void onNewMessage(ChatInfo message) {
        if (message.type.equals(ChatListenerService.CHAT_HISTORY)) {
            String history = message.payload;
            if (history != null && !history.equals(""))
                mViewModel.setMessages(history);
        } else if (message.type.equals(ChatConstants.SEND_MESSAGE)) {
            try {
                String payload = message.payload;
                ChatMessage chatMessage = mViewModel.getObjectMapper()
                        .readValue(payload, ChatMessage.class);
                if (chatMessage.fromId.equals(mUserId)) {
                    if (newMessages.containsKey(chatMessage.key)) {
                        ChatMessage msg = newMessages.get(chatMessage.key);
                        if (msg == null) return;
                        msg.id = chatMessage.id;
                        if (!msg.messageType.equals("5")) return;
                        tryDeleteMessage(msg);
                        newMessages.remove(msg.key);
                    }
                    return;
                }
                if (mViewModel != null) {
                    Chat chat = mViewModel.getOldMessagesData().getValue();
                    if (chat != null)
                        if (!chat.groupId.equals(chatMessage.groupId))
                            return;
                } else {
                    return;
                }
                ((ChatDetailAdapter) mLstChatMessages.getAdapter()).addMessage(chatMessage);
                try {
                    if (mViewModel != null)
                        if (mViewModel.getOldMessagesData().getValue() != null)
                            sendReadAll(mViewModel.getOldMessagesData().getValue().groupId);
                } catch (Exception e) {
                    Bugsnag.notify(new RuntimeException(e));
                    M.log(e.getMessage());
                }
            } catch (Exception e) {
                Bugsnag.notify(new RuntimeException(e));
                M.log(e.getMessage());
            }
        }
    }

    @Override
    public void onPermissionGranted() {
        requestPermission(Permissions.CAMERA_AND_STORAGE_REQUEST_CODE + Permissions.RECORD_AUDIO_REQUEST_CODE,
                Permissions.READ_EXTERNAL_STORAGE, Permissions.CAMERA, Permissions.RECORD_AUDIO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Permissions.CAMERA_AND_STORAGE_REQUEST_CODE + Permissions.RECORD_AUDIO_REQUEST_CODE) {
            boolean success = false;
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    success = true;
                }
            }

            if (success) {
                showSelectionDialog();
            }
        }
    }

    private void showSelectionDialog() {

        PickerDialog dialog = new PickerDialog();
        dialog.setCancelable(true);
        dialog.setListener(this);
        dialog.show(getChildFragmentManager(), "picker");
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode != CAPTURE_IMAGE_REQUEST_CODE && data == null) return;
        if (requestCode == SELECT_IMAGE_REQUEST_CODE || requestCode == SELECT_VIDEO_REQUEST_CODE
                || requestCode == SELECT_AUDIO_REQUEST_CODE || requestCode == CAPTURE_IMAGE_REQUEST_CODE
                || requestCode == CAPTURE_VIDEO_REQUEST_CODE || requestCode == RECORD_AUDIO_REQUEST_CODE) {

            String path;
            if (requestCode != CAPTURE_IMAGE_REQUEST_CODE)
                path = FileUtil.getPath(getActivity(), data.getData());
            else path = mCurrentPhotoPath;
            if (path != null) {
                if (mLstChatMessages.getAdapter() == null) {
                    ChatDetailAdapter adapter = new ChatDetailAdapter(getActivity(), mLstChatMessages);
                    adapter.setDeleteListener(this);
                    mLstChatMessages.setAdapter(adapter);
                    mLstChatMessages.setLayoutManager(new LinearLayoutManager(getActivity()));
                }
                String key = System.currentTimeMillis() + "-" + mViewModel.getGroupId() + "-" + mLstChatMessages.getAdapter().getItemCount();
                final ChatMessage chatMessage = new ChatMessage();
                chatMessage.content = path;
                chatMessage.fromId = mUserId;
                chatMessage.groupId = mViewModel.getGroupId();
                chatMessage.senderImage = mUserImage;
                chatMessage.senderName = mUserName;
                chatMessage.key = key;
                String type;
                if (requestCode == SELECT_IMAGE_REQUEST_CODE || requestCode == CAPTURE_IMAGE_REQUEST_CODE) {
                    chatMessage.messageType = "3";
                    type = "image";
                } else if (requestCode == SELECT_VIDEO_REQUEST_CODE || requestCode == CAPTURE_VIDEO_REQUEST_CODE) {
                    chatMessage.messageType = "4";
                    type = "video";
                } else {
                    chatMessage.messageType = "6";
                    type = "audio";
                }
                chatMessage.thumbNail = path;
                newMessages.put(key, chatMessage);
                if (mLstChatMessages.getAdapter() != null) {
                    ((ChatDetailAdapter) mLstChatMessages.getAdapter()).addMessage(chatMessage);
                    scrollToBottom();
                }
                if (mLstChatMessages != null)
                    mLstChatMessages.scrollToPosition(mLstChatMessages.getAdapter().getItemCount() - 1);
                final int count = mLstChatMessages.getAdapter().getItemCount();
                File file = new File(path);

                String uploadPath = getString(R.string.chat_file_upload);

                HashMap<String, String> headerMap = new HashMap<>(2);
                headerMap.put("api_token", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_ACCESS_TOKEN, ""));
                headerMap.put("userid", PreferenceHelper.getString(getActivity(), PreferenceHelper.KEY_USER_ID, ""));

                HashMap<String, RequestBody> params = new HashMap<>(2);
                params.put("type", RequestHelper.getRequestBody(type));

                new UploadChatFileRequest(TjssNetworkInterface.class, new RequestCallback<ChatFileUploadResponse>() {
                    @Override
                    public void onSuccess(ChatFileUploadResponse result) {
                        if (mLstChatMessages != null)
                            if (mLstChatMessages.getAdapter() != null) {
                                final ChatMessage msg =
                                        ((ChatDetailAdapter) mLstChatMessages.getAdapter()).getMessageAt(count - 1);
                                msg.progress = 100;
                                msg.content = result.image;
                                mLstChatMessages.getAdapter().notifyItemChanged(count - 1);
                                mLstChatMessages.scrollToPosition(count - 1);
                                JSONObject jsonObject = new JSONObject();
                                try {

                                    jsonObject.put(ChatConstants.MESSAGE_CONTENT, result.image);
                                    jsonObject.put(ChatConstants.MESSAGE_FROM_ID, mUserId);
                                    jsonObject.put(ChatConstants.MESSAGE_GROUP_ID, mViewModel.getGroupId());
                                    jsonObject.put(ChatConstants.MESSAGE_SENDER_NAME, mUserName);
                                    jsonObject.put(ChatConstants.MESSAGE_SENDER_IMAGE, mUserImage);
                                    jsonObject.put(ChatConstants.THUMBNAIL, result.thumbnail);
                                    jsonObject.put(ChatConstants.MESSAGE_TYPE, msg.messageType);
                                    jsonObject.put(ChatConstants.KEY, msg.key);

                                    Intent intent = new Intent();
                                    intent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
                                    intent.putExtra(ChatConstants.EXTRA, ChatConstants.SEND_MESSAGE);
                                    intent.putExtra(ChatConstants.EXTRA_PAYLOAD, jsonObject.toString());
                                    LocalBroadcastManager.getInstance(getActivity())
                                            .sendBroadcast(intent);

                                } catch (Exception e) {
                                    Bugsnag.notify(new RuntimeException(e));
                                    M.log(e.getMessage());
                                }
                            }
                    }

                    @Override
                    public void onFailure(String reason) {
                        chatMessage.progress = 100;
                        mLstChatMessages.getAdapter().notifyItemChanged(count - 1);
                    }
                }).upload(uploadPath, headerMap, params, RequestHelper.getMultipartBodyPart("chatFile", file));
            }
        }
    }

    @Override
    public void onItemDeleted(ChatMessage chatMessage) {
        if (chatMessage.id <= 0) {
            //wait for the id, we'll delete the message later
            return;
        }
        tryDeleteMessage(chatMessage);
    }

    private void tryDeleteMessage(ChatMessage chatMessage) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(ChatConstants.MESSAGE_ID, chatMessage.id);
            Intent intent = new Intent();
            intent.setAction(ChatListenerService.OUTGOING_CHAT_ACTION);
            intent.putExtra(ChatConstants.EXTRA, ChatConstants.DELETE_MESSAGE);
            intent.putExtra(ChatConstants.EXTRA_PAYLOAD, jsonObject.toString());
            LocalBroadcastManager.getInstance(getActivity())
                    .sendBroadcast(intent);
        } catch (Exception e) {
            Bugsnag.notify(new RuntimeException(e));
            M.log(e.getMessage());
        }
    }

    @Override
    public void onSelected(int type) {
        switch (type) {
            case PickerDialog.IMAGE_GALLERY:
                Intent imageIntent = new Intent(Intent.ACTION_GET_CONTENT/*Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI*/);
                imageIntent.setType("image/*");
                startActivityForResult(Intent.createChooser(imageIntent, "Choose Image"), SELECT_IMAGE_REQUEST_CODE);
                break;

            case PickerDialog.IMAGE_CAMERA:
                if (!havePermission(Permissions.CAMERA)) {
                    M.showToast(getActivity(), "cannot open camera with out permission, permission denied");
                    return;
                }

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    Bugsnag.notify(new RuntimeException(ex));
                    M.log(ex.getMessage());
                }

                if (photoFile != null) {
                    Uri photoURI = Uri.fromFile(photoFile);
                    Intent cameraImageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(Intent.createChooser(cameraImageIntent, "Capture Image"), CAPTURE_IMAGE_REQUEST_CODE);
                }


                break;

            case PickerDialog.VIDEO_GALLERY:
                Intent videoIntent = new Intent(Intent.ACTION_GET_CONTENT);
                videoIntent.setType("video/*");
                startActivityForResult(Intent.createChooser(videoIntent, "Choose Video"), SELECT_VIDEO_REQUEST_CODE);
                break;

            case PickerDialog.VIDEO_CAMERA:
                if (!havePermission(Permissions.CAMERA)) {
                    M.showToast(getActivity(), "cannot open camera with out permission, permission denied");
                    return;
                }

                Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, Environment.getExternalStorageDirectory().getPath() + "videocapture_example.mp4");

                startActivityForResult(Intent.createChooser(takeVideoIntent, "Capture Video"), CAPTURE_VIDEO_REQUEST_CODE);
                break;

            case PickerDialog.AUDIO_GALLERY:
                Intent audioIntent = new Intent(Intent.ACTION_GET_CONTENT);
                audioIntent.setType("audio/*");
                startActivityForResult(Intent.createChooser(audioIntent, "Choose Video"), SELECT_AUDIO_REQUEST_CODE);
                break;

            case PickerDialog.AUDIO_RECORD:
                if (!havePermission(Permissions.RECORD_AUDIO)) {
                    M.showToast(getActivity(), "Unable to record audio with out permission, permission denied");
                    return;
                }

                Intent recordAudioIntent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                startActivityForResult(Intent.createChooser(recordAudioIntent, "Record Audio"), RECORD_AUDIO_REQUEST_CODE);
                break;
            case PickerDialog.INVALID:
                break;
        }
    }
}
