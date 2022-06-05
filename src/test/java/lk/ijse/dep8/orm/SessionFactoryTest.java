package lk.ijse.dep8.orm;


import lk.ijse.dep8.orm.entity.Student;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class SessionFactoryTest {
    public static void main(String[] args) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dep08_simple_orm_db?createDatabaseIfNotExist=true", "root", "mysql");
            new SessionFactory().addAnnotationClass(Student.class)
                    .addConnection(connection)
                    .build()
                    .bootstrap();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create the Simple_orm_db");
        }
    }
}