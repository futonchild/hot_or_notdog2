package com.futonchild.android.hotornotdog;

import android.app.Activity;

// this was a dead end
public class ClassifierBuilder implements Runnable {
    private Classifier mClassifier ;
    private Activity mActivity ;

    public ClassifierBuilder(Activity am) {
        mActivity = am ;
    }

    public void run() {
        mClassifier = ImageClassifierFactory.INSTANCE.create(
                mActivity.getAssets(),
                ConstantsKt.GRAPH_FILE_PATH,
                ConstantsKt.LABELS_FILE_PATH,
                ConstantsKt.IMAGE_SIZE,
                ConstantsKt.GRAPH_INPUT_NAME,
                ConstantsKt.GRAPH_OUTPUT_NAME
        );
    }

    public Classifier getClassifier() {
        return mClassifier ;
    }
}
