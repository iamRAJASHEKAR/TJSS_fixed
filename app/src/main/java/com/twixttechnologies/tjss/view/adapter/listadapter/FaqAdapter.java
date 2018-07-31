package com.twixttechnologies.tjss.view.adapter.listadapter;

import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoviussoftwaresolution.tjss.R;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import com.twixttechnologies.tjss.model.internal.FaqQuestion;

import java.util.List;

/**
 * @author Sony Raj on 13-09-17.
 */

public class FaqAdapter extends ExpandableRecyclerViewAdapter<FaqAdapter.FaqQuestionViewHolder, FaqAdapter.FaqAnswerViewHolder> {


    public FaqAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public FaqQuestionViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        return new FaqQuestionViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.extra_faq_header, parent, false));
    }

    @Override
    public FaqAnswerViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        return new FaqAnswerViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.extra_faq_answer, parent, false));
    }

    @Override
    public void onBindChildViewHolder(FaqAnswerViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final FaqQuestion question = (FaqQuestion) group;
        holder.mAnswer.setText(question.getItems().get(0).getAnswer());
    }

    @Override
    public void onBindGroupViewHolder(FaqQuestionViewHolder holder, int flatPosition, ExpandableGroup group) {
        final FaqQuestion question = (FaqQuestion) group;
        holder.mHeader.setText(question.getTitle());
    }

    class FaqQuestionViewHolder extends GroupViewHolder {


        AppCompatTextView mHeader;

        FaqQuestionViewHolder(View itemView) {
            super(itemView);
            mHeader = (AppCompatTextView) itemView.findViewById(R.id.lblFaqHeader);
        }

        @Override
        public void expand() {
            super.expand();
            if (mHeader != null)
                mHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up_thin, 0);
        }

        @Override
        public void collapse() {
            super.collapse();
            if (mHeader != null)
                mHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down_thin, 0);
        }
    }


    class FaqAnswerViewHolder extends ChildViewHolder {

        AppCompatTextView mAnswer;

        FaqAnswerViewHolder(View itemView) {
            super(itemView);
            mAnswer = (AppCompatTextView) itemView.findViewById(R.id.lblFaqAnswer);
        }
    }

}
