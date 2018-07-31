package com.innoviussoftwaresolution.tjss.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.innoviussoftwaresolution.tjss.R;
import com.innoviussoftwaresolution.tjss.model.internal.FaqAnswer;
import com.innoviussoftwaresolution.tjss.model.internal.FaqQuestion;
import com.innoviussoftwaresolution.tjss.model.network.request.RequestCallback;
import com.innoviussoftwaresolution.tjss.model.network.response.Faq;
import com.innoviussoftwaresolution.tjss.model.repository.TjssRepository;
import com.innoviussoftwaresolution.tjss.utils.M;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FaqViewModel extends ViewModelBase implements RequestCallback<List<Faq>> {

    private final MutableLiveData<List<FaqQuestion>> mFaqs
            = new MutableLiveData<>();


    private FaqViewModel(Application application) {
        super(application);

        new TjssRepository().getFaqs(getApplication().getString(R.string.faq_path), this);

    }

    public MutableLiveData<List<FaqQuestion>> getFaqs() {
        return mFaqs;
    }

    @Override
    public void onSuccess(List<Faq> result) {
        if (result == null || result.size() <= 0) {
            M.log("Faq chatItems is empty");
            return;
        }

        for (int i = 0; i < result.size(); i++) {
            Faq x = result.get(i);
            if (x == null) continue;

            ArrayList<FaqAnswer> answers = new ArrayList<>(1);
            FaqAnswer answer = new FaqAnswer(x.answer);
            answers.add(answer);

            List<FaqQuestion> questions = new ArrayList<>(result.size());
            FaqQuestion question = new FaqQuestion(x.questions, answers);
            questions.add(question);

            mFaqs.setValue(questions);
        }

    }

    @Override
    public void onFailure(String reason) {

    }


    @SuppressWarnings("unchecked")
    public static class FaqViewModelFactory implements ViewModelProvider.Factory {

        private final Application mApplication;

        public FaqViewModelFactory(Application application) {
            mApplication = application;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new FaqViewModel(mApplication);
        }
    }

}
