package ru.models;

public  class AboutProcesses {
    private String statusSetByClient;
    private String statusSetByManager;
    private String statusSetBySystem;
    private String messageWhileRejected;

    public  final String getStatusSetByClient() {
        return statusSetByClient;
    }


    public final String getMessageWhileRejected() {
        return messageWhileRejected;
    }

    public final String getStatusSetBySystem() {
        return statusSetBySystem;
    }

    public final String getStatusSetByManager() {
        return statusSetByManager;
    }

    public  void setStatusSetByClient(String statusSetByClient) {
        this.statusSetByClient = statusSetByClient;
    }

    public void setStatusSetByManager(String statusSetByManager) {
        this.statusSetByManager = statusSetByManager;
    }

    public void setStatusSetBySystem(String statusSetBySystem) {
        this.statusSetBySystem = statusSetBySystem;
    }

    public void setMessageWhileRejected(String messageWhileRejected) {
        this.messageWhileRejected = messageWhileRejected;
    }
}
