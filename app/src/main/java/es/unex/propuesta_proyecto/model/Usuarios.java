package es.unex.propuesta_proyecto.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
/*Esta clase define los atributos y metodos de la tabla "Usuarios" de Room */

@Entity(tableName = "Usuarios")
public class Usuarios {

    @PrimaryKey
    @NonNull
    private String name;
    private String password;

    public Usuarios(String name, String password) {
        this.name = name;
        this.password = password;
    }

    @Ignore
    public Usuarios() {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
