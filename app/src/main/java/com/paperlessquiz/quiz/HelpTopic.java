package com.paperlessquiz.quiz;

public class HelpTopic {
    int idHelpTopic, orderNr, Type, idUser;
    String remark, response;

    public HelpTopic(int idHelpTopic, int orderNr, int type, int idUser, String remark, String response) {
        this.idHelpTopic = idHelpTopic;
        this.orderNr = orderNr;
        Type = type;
        this.idUser = idUser;
        this.remark = remark;
        this.response = response;
    }

    public int getType() {
        return Type;
    }

    public String getRemark() {
        return remark;
    }

    public String getResponse() {
        return response;
    }
}
