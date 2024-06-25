package sust.muro.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "messages")
public class Message {

  @Id
  @GeneratedValue
  Long id;

  @Column(columnDefinition = "TEXT")
  String message;

  @Column(updatable = false)
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date created;

  @Column()
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  Date updated;

  @Column(columnDefinition = "int not null default 0")
  int likes;

  // vamos a configurar para que al crear un usuario, se guarde la marca temporal
  // de forma automática
  @PrePersist
  protected void onCreate() {
    this.created = new Date();
  }

  @PreUpdate
  protected void onUpdate() {
    this.updated = new Date();
  }

  @ManyToOne
  @JoinColumn(name = "user_id", referencedColumnName = "id")
  User user;

  @OneToMany(mappedBy = "message")
  List<Comment> comments;

  // atributos no columnas
  String creatorsName;
}
