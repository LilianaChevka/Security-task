package com.hometask.task_security.repository.db_repo;

import com.hometask.task_security.model.Email;
import com.hometask.task_security.model.SqlRequestMessages;
import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import javax.mail.Message;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JDBCRepoImpl implements JDBCRepo {

    private Connection dbConnection;
    private static final String URL ="jdbc:mysql://localhost:3306/db_access";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";

    @SneakyThrows
    private Connection getDbConnection() {
        Class.forName("com.mysql.jdbc.Driver");
        this.dbConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        this.dbConnection.setAutoCommit(false);
        return this.dbConnection;
    }

    public JDBCRepoImpl() throws SQLException {
    }


    @SneakyThrows
    @Override
    public List<Email> findAllEmails() {
            List<Email> emails = new ArrayList();
            PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_SELECT_ALL.getRequest());
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Email email = new Email();
                email.setId((int) resultSet.getLong("id"));
                email.setRecipient(resultSet.getString("recipient"));
                email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setDate(resultSet.getDate("date"));

                emails.add(email);
            }

            return emails;
        }
    @SneakyThrows
    @Override
    public void save(Email email) {
            PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_INSERT.getRequest());
            statement.setString(1, email.getRecipient());
            statement.setString(2, email.getSubject());
            statement.setString(3, email.getBody());
            statement.setString(4, email.getDate());

            statement.executeUpdate();

        }



    @SneakyThrows
    @Override
    public Email getById(int id) {

            Email email = new Email();
            PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_SELECT_MESSAGE_ID.getRequest());
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                email.setRecipient();resultSet.getInt("recipient"));
                email.setSubject(resultSet.getString("subject"));
                email.setBody(resultSet.getString("body"));
                email.setDate(resultSet.getDate("email_text"));

            }

            return email;

        }

    @Override
    @SneakyThrows
    public void update(int id) {

            PreparedStatement preparedStatement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_UPDATE_TIME.getRequest());
            preparedStatement.setInt(1,3);

            preparedStatement.executeUpdate();

        }


    @SneakyThrows
    @Override
    public void deleteById(int id) {

            PreparedStatement statement = this.getDbConnection().prepareStatement(SqlRequestMessages.SQL_DELETE_MESSAGE_ID.getRequest());
            statement.setLong(1, id);
            statement.execute();

        }


    @Override
    public List<Email> emailsForSending() {
        LocalDate time = LocalDate.now();
        List<Email> emailList = findAllEmails();
        List<Email> result = new ArrayList<>();

        for (Email email : emailList) {
            if (time.equals(email.getDate())) {
                result.add(email);
            }
        }
        return result;
    }
}
