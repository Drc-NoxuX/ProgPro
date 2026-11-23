package com.ProgPro.Managers;

import com.ProgPro.Dialog;

public class DialogManager {

    private static DialogManager instance;
    private Dialog dialog;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if(instance == null){
            instance =  new DialogManager();
        }
        return instance;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
