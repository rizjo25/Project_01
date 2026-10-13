package roadaccident.dao;

import roadaccident.model.Registration;
import roadaccident.util.DatabaseConnection;
import roadaccident.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class RegistrationDAO {

    public boolean phoneNumberExists(String phoneNumber)
            throws SQLException {

        String normalizedPhoneNumber = normalizeText(phoneNumber);
        if (normalizedPhoneNumber == null) {
            return false;
        }

        String sql = """
            SELECT user_id
            FROM users
            WHERE phone_number = ?
            """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            statement.setString(1, normalizedPhoneNumber);

            try (ResultSet result =
                         statement.executeQuery()) {

                return result.next();
            }
        }
    }

    public boolean register(Registration registration)
            throws SQLException {

        if (registration == null) {
            return false;
        }

        String phoneNumber = normalizeText(registration.getPhoneNumber());
        if (phoneNumber == null || phoneNumber.isEmpty()) {
            return false;
        }

        if (phoneNumberExists(phoneNumber)) {
            return false;
        }

       String sql = """
    INSERT INTO users (
        full_name,
        phone_number,
        password_hash,
        role,
        zone,
        driving_licence_number,
        ambulance_number,
        hospital_name,
        designation,
        police_station,
        badge_number,
        verification_status
    )
    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'PENDING')
    """;

        try (Connection connection =
                     DatabaseConnection.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(sql)) {

            /*
             * For a classroom prototype this line uses the password
             * from the model. Replace it with a real hashing method
             * before using the application with real users.
             */
            String passwordHash =
                    registration.getPassword();

            statement.setString(
                    1,
                    registration.getFullName()
            );

            statement.setString(
                    2,
                    registration.getPhoneNumber()
            );

            statement.setString(
                    3,
                    passwordHash
            );

            statement.setString(
                    4,
                    convertRole(
                            registration.getRole()
                    )
            );

            statement.setString(
                    5,
                    convertZone(
                            registration.getZone()
                    )
            );

            statement.setString(
                    6,
                    emptyToNull(
                            registration
                                    .getDrivingLicenceNumber()
                    )
            );

            statement.setString(
                    7,
                    emptyToNull(
                            registration
                                    .getAmbulanceNumber()
                    )
            );

            statement.setString(
                    8,
                    emptyToNull(
                            registration
                                    .getHospitalName()
                    )
            );

            statement.setString(
                    9,
                    emptyToNull(
                            registration
                                    .getDesignation()
                    )
            );

            statement.setString(
                    10,
                    emptyToNull(
                            registration
                                    .getPoliceStation()
                    )
            );

            statement.setString(
                    11,
                    emptyToNull(
                            registration
                                    .getBadgeNumber()
                    )
            );

            int rowsInserted =
                    statement.executeUpdate();

            return rowsInserted > 0;
        }
    }

    private String normalizeText(String value) {
        if (value == null) {
            return null;
        }

        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String emptyToNull(String value) {
        return normalizeText(value);
    }

    private String convertRole(String role) {
        String normalizedRole = normalizeText(role);
        if (normalizedRole == null) {
            return null;
        }

        switch (normalizedRole.toLowerCase(Locale.ROOT)) {
            case "ambulance driver":
                return "AMBULANCE_DRIVER";
            case "hospital staff":
                return "HOSPITAL_STAFF";
            case "police":
                return "POLICE";
            default:
                return normalizedRole;
        }
    }

    private String convertZone(String zone) {
        String normalizedZone = normalizeText(zone);
        if (normalizedZone == null) {
            return null;
        }

        switch (normalizedZone.toLowerCase(Locale.ROOT)) {
            case "kozhikode":
                return "KOZHIKODE";
            case "westhill":
                return "WESTHILL";
            case "easthill":
                return "EASTHILL";
            default:
                return normalizedZone;
        }
    }
    public User login(
        String phoneNumber,
        String password) throws SQLException {

    String sql = """
        SELECT
            user_id,
            full_name,
            phone_number,
            password_hash,
            role,
            zone,
            verification_status,
            hospital_name
        FROM users
        WHERE phone_number = ?
        AND verification_status = 'APPROVED'
        """;

    try (Connection connection =
                 DatabaseConnection.getConnection();
         PreparedStatement statement =
                 connection.prepareStatement(sql)) {

        statement.setString(1, phoneNumber);

        try (ResultSet result =
                     statement.executeQuery()) {

            if (!result.next()) {
                return null;
            }

            String storedPassword =
                    result.getString("password_hash");

            /*
             * Current classroom prototype:
             * compares the entered password with the test password
             * stored in the database.
             *
             * Final version:
             * replace with BCrypt / Argon2 password verification.
             */
            if (!password.equals(storedPassword)) {
                return null;
            }

            return new User(
                    result.getInt("user_id"),
                    result.getString("full_name"),
                    result.getString("phone_number"),
                    result.getString("role"),
                    result.getString("zone"),
                    result.getString("verification_status"),
                    result.getString("hospital_name")
            );
        }
    }
}
}
