package main.java.DB;

import org.json.JSONArray;
import org.json.JSONObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Project {
    public int id;
    public int contractId;
    public String deadline;
    public double progressPercentage;
    public String projectName;
    public String projectDescription;
    public String projectStatus;
    private final static DBLoader db = DBLoader.getInstance();

    public Project (int id, int contractId, String deadline, double progressPercentage,
                    String projectName, String projectDescription, String projectStatus) {
        this.id = id;
        this.contractId = contractId;
        this.deadline = deadline;
        this.progressPercentage = progressPercentage;
        this.projectName = projectName;
        this.projectDescription = projectDescription;
        this.projectStatus = projectStatus;
    }

    public static JSONArray getAllProjects(String username, String userType) {
        // Tested - Works
        JSONArray projects = new JSONArray();
        try {
            String query;
            if (userType == "Provider") {
                query = "SELECT * FROM project WHERE providerId=?";
            } else {
                query = "SELECT * FROM project WHERE clientId=?";
            }
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setString(1, username);
            ResultSet res = statement.executeQuery();
            if (res != null){
                while (res.next()) {
                    JSONObject project = sqlSchemaToJSON(res);
                    projects.put(project);
                }
            }
        } catch(Exception e) {
            System.out.println(e);
        }
        return projects;
    }

    public static String addNewProject(JSONObject req) {
        // Tested - Works
        try{
            String query="INSERT into project (projectId, providerId, clientId, rate," +
                    "deadline, progressPercentage, projectName, projectDescription, " +
                    "projectStatus) values (null, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement=db.getDBConnection().prepareStatement(query);
            statement.setString(1, req.getString("providerId"));
            statement.setString(2, req.getString("clientId"));
            statement.setDouble(3, req.getDouble("hourlyRate"));
            statement.setString(4, req.getString("deadline"));
            statement.setDouble(5, req.getDouble("progressPercentage"));
            statement.setString(6, req.getString("projectName"));
            statement.setString(7, req.getString("projectDescription"));
            statement.setString(8, req.getString("projectStatus"));
            statement.execute();
        }catch(Exception e){
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static JSONObject sqlSchemaToJSON(ResultSet res) throws SQLException {
        JSONObject project = new JSONObject();
        project.put("projectId", res.getInt("projectId"));
        project.put("providerId", res.getString("providerId"));
        project.put("clientId", res.getString("clientId"));
        project.put("hourlyRate", res.getDouble("hourlyRate"));
        project.put("deadline", res.getString("deadline"));
        project.put("progressPercentage", res.getDouble("progressPercentage"));
        project.put("projectName", res.getString("projectName"));
        project.put("projectDescription", res.getString("projectDescription"));
        project.put("projectStatus", res.getString("projectStatus"));
        return project;
    }

    public static void main(String[] args) {

//        Project project2 = Project.getProjectWithContractId(1);
//        System.out.println(project2.projectName);

//        JSONObject testObject = new JSONObject();
//        testObject.put("contractId", 2);
//        testObject.put("deadline", "Jan 5, 2022");
//        testObject.put("progressPercentage", 99.13);
//        testObject.put("projectName", "Tester Name");
//        testObject.put("projectDescription", "Test desc");
//        testObject.put("projectStatus", "In Progress");
//        System.out.println(Project.addNewProject(testObject));
    }
}
