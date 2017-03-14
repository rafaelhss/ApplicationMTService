package com.elcartero.domain;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A MessageMT.
 */
@Entity
@Table(name = "message_mt")
public class MessageMT implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "destination", nullable = false)
    private String destination;

    @NotNull
    @Column(name = "source", nullable = false)
    private String source;

    @NotNull
    @Column(name = "body", nullable = false)
    private String body;

    @NotNull
    @Column(name = "secret_token", nullable = false)
    private String secretToken;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public MessageMT destination(String destination) {
        this.destination = destination;
        return this;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getSource() {
        return source;
    }

    public MessageMT source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getBody() {
        return body;
    }

    public MessageMT body(String body) {
        this.body = body;
        return this;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public MessageMT secretToken(String secretToken) {
        this.secretToken = secretToken;
        return this;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MessageMT messageMT = (MessageMT) o;
        if (messageMT.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, messageMT.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MessageMT{" +
            "id=" + id +
            ", destination='" + destination + "'" +
            ", source='" + source + "'" +
            ", body='" + body + "'" +
            ", secretToken='" + secretToken + "'" +
            '}';
    }
}
