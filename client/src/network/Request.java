package client.src.network;

import client.src.builders.LabWork;
import client.src.com.Command;

public class Request {
    private LabWork labWork;
    private Command command;
    private String args;
    //public Object args;
    public Request(Command command) {
        this.command = command;
    }
    public Request(Command command, LabWork labWork) {
        this.command = command;
        this.labWork = labWork;
    }
    public Request(Command command, String args) {
        this.command = command;
        this.args = args;
    }
    public Request(Command command, LabWork labWork, String args) {
        this.command = command;
        this.labWork = labWork;
        this.args = args;
    }
    public Request(Command command, Object args) {
        this.command = command;
        this.args = args;
    }
    public Command getCommand() {
        return command;
    }
    public LabWork getLabWork() {
        return labWork;
    }
    public String getArgs() {
        return args;
    }
}
