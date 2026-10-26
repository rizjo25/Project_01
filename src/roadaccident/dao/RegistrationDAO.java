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
        String fullName = normalizeText(registration.getFullName());
        String passwordHash = registration.getPassword();

        if (phoneNumber == null || fullName == null
                || passwordHash == null || passwordHash.isBlank()) {
            return false;
        }

        if (phoneNumberExists(phoneNumber)) {
            return false;
        }

        String normalizedRole = convertRole(registration.getRole());
        String normalizedZone = convertZone(registration.getZone());

        if (normalizedRole == null || normalizedZone == null) {
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

            statement.setString(
                    1,
                    fullName
            );

            statement.setString(
                    2,
                    phoneNumber
            );

            statement.setString(
                    3,
                    passwordHash
            );

            statement.setString(
                    4,
                    normalizedRole
            );

            statement.setString(
                    5,
                    normalizedZone
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
            case "ambulance_driver":
                return "AMBULANCE_DRIVER";
            case "hospital staff":
            case "hospital_staff":
                return "HOSPITAL_STAFF";
            case "police":
                return "POLICE";
            case "admin":
                return "ADMIN";
            default:
                return normalizedRole.toUpperCase(Locale.ROOT).replace(' ', '_');
        }
    }

    private String convertZone(String zone) {
        String normalizedZone = normalizeText(zone);
        if (normalizedZone == null) {
            return null;
        }

        String withoutSuffix = normalizedZone
                .toUpperCase(Locale.ROOT)
                .replace("_", " ")
                .replace("-", " ")
                .trim();

        if (withoutSuffix.endsWith(" ZONE")) {
            withoutSuffix = withoutSuffix.substring(
                    0,
                    withoutSuffix.length() - " ZONE".length()
            ).trim();
        }

        switch (withoutSuffix) {
            case "KOZHIKODE":
                return "KOZHIKODE";
            case "WESTHILL":
                return "WESTHILL";
            case "EASTHILL":
                return "EASTHILL";
            default:
                return withoutSuffix;
        }
    }

    public User login(
        String phoneNumber,
        String password) throws SQLException {

    String normalizedPhoneNumber = normalizeText(phoneNumber);
    if (normalizedPhoneNumber == null || password == null) {
        return null;
    }

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

        statement.setString(1, normalizedPhoneNumber);

        try (ResultSet result =
                     statement.executeQuery()) {

            if (!result.next()) {
                return null;
            }

            String storedPassword =
                    result.getString("password_hash");

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
