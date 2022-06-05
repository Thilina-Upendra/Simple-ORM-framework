package lk.ijse.dep8.orm;

import lk.ijse.dep8.orm.annotation.Entity;
import lk.ijse.dep8.orm.annotation.Id;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SessionFactory {

    private final List<Class<?>> entityClassList = new ArrayList<>();

    private Connection connection;

    public SessionFactory addAnnotationClass(Class<?> entityClass){
        if(entityClass.getDeclaredAnnotation(Entity.class) == null){
            throw new RuntimeException("Invalid Entity class");
        }
        entityClassList.add(entityClass);
        return this;
    }

    public SessionFactory addConnection(Connection connection){
        this.connection = connection;
        return this;
    }

    public SessionFactory build(){
        if(connection == null){
            throw new RuntimeException("Cannot proceed without a Connection");
        }
        return this;
    }

    public void bootstrap(){
        /*Add all the entities to the entity class list*/
        for (Class<?> entity:entityClassList) {
            String tableName = entity.getDeclaredAnnotation(Entity.class).value();
            if(tableName.trim().isEmpty()){
                tableName = entity.getSimpleName();
            }

            List<String> columns = new ArrayList<>();
            String primaryKey = null;
            Field[] fields = entity.getDeclaredFields();

            for (Field field:fields) {
                Id primaryKeyField = field.getDeclaredAnnotation(Id.class);
                if(primaryKeyField != null){
                    primaryKey = field.getName();
                    continue;
                }

                String columnName = field.getName();
                columns.add(columnName);
            }

            if(primaryKey == null){
                throw new RuntimeException("Entity without a primary key is Invalid.");
            }

            /*Create a String builder*/
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE IF NOT EXIST ").append("( ");
            for (String column:columns) {
                sb.append(column).append(" VARCHAR(250) ");
            }

            sb.append(primaryKey).append(" VARCHAR(255) PRIMARY KEY);");

            System.out.println(sb);
            Statement stm = null;
            try {
                stm = connection.createStatement();
                stm.execute(sb.toString());
            } catch (SQLException e) {
                throw new RuntimeException("There is an issue in the connection");
            }

        }
    }

}
