package ru.example.websocket;

public class ClientMsg {

    private String fromLogin;
    private String msg;

    public ClientMsg(String fromLogin, String msg) {
        this.fromLogin = fromLogin;
        this.msg = msg;
    }

    public ClientMsg() {
    }

    public String getFromLogin() {
        return fromLogin;
    }

    public void setFromLogin(String fromLogin) {
        this.fromLogin = fromLogin;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
