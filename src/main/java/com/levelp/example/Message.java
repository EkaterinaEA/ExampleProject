package com.levelp.example;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Past;
import java.util.Date;

@NamedQueries({
        @NamedQuery(name = Message.FIND_MESSAGE_ID_QUERY, query = "from Message where id = : id"),
        @NamedQuery(name = Message.FIND_MESSAGE_CLIENT_QUERY, query = "from Message where client = : client"),
})

@Entity
@Table(name = "messages")
public class Message {

    public static final String FIND_MESSAGE_ID_QUERY = "findMessageById";
    public static final String FIND_MESSAGE_CLIENT_QUERY = "findMessageByClient";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "message_id_generator")
    @SequenceGenerator(name = "message_id_generator", sequenceName = "message_id_seq")
    @Column(name = "message_id")
    private long messageId;

    @Column(name = "subject")
    private String subject;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "attachedFiles")
    private String attachedFiles;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    @Past
    private Date lastModificationTime;

    @ManyToOne
    private Admin admin;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Room room;

    public Message() {}

    public Message(String text, String attechedFiles, Room room) {
        this.text = text;
        this.attachedFiles = attechedFiles;
        this.room = room;
    }


    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getAttachedFiles() {
        return attachedFiles;
    }

    public void setAttachedFiles(String attachedFiles) {
        this.attachedFiles = attachedFiles;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }
}

