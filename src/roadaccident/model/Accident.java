package roadaccident.model;

public class Accident {

    private String reporterName;
    private String reporterPhone;
    private String accidentDate;
    private String accidentTime;
    private String location;
    private String accidentType;
    private String severity;
    private int victimCount;
    private String description;

    public Accident(
            String reporterName,
            String reporterPhone,
            String accidentDate,
            String accidentTime,
            String location,
            String accidentType,
            String severity,
            int victimCount,
            String description) {

        this.reporterName = reporterName;
        this.reporterPhone = reporterPhone;
        this.accidentDate = accidentDate;
        this.accidentTime = accidentTime;
        this.location = location;
        this.accidentType = accidentType;
        this.severity = severity;
        this.victimCount = victimCount;
        this.description = description;
    }

    public String getReporterName() {
        return reporterName;
    }

    public String getReporterPhone() {
        return reporterPhone;
    }

    public String getAccidentDate() {
        return accidentDate;
    }

    public String getAccidentTime() {
        return accidentTime;
    }

    public String getLocation() {
        return location;
    }

    public String getAccidentType() {
        return accidentType;
    }

    public String getSeverity() {
        return severity;
    }

    public int getVictimCount() {
        return victimCount;
    }

    public String getDescription() {
        return description;
    }
}
