import repository.DatabaseConnection;
import view.MainMenuView;

import java.sql.Connection;
import java.sql.SQLException;

void main() {
    try (Connection dbConnection = DatabaseConnection.getConnection()) {
        MainMenuView.mainMenuView();

    } catch (SQLException e) {
        IO.println(e.getMessage());
    }
}