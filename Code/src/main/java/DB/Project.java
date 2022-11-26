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

    public static JSONArray getAllProjects() {
        // Tested - Works
        JSONArray projects = new JSONArray();
        try {
            String query = "SELECT * from project";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
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

    public static Project getProjectWithId(int id) {
        // Tested - Works
        Project project = null;
        try {
            String query = "SELECT * from project WHERE projectId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();
            project = sqlSchemaToProject(res);
        } catch(Exception e) {
            System.out.println(e);

        }
        return project;
    }

    public static Project getProjectWithContractId(int id) {
        // Tested - Works
        Project project = null;
        try {
            String query = "SELECT * from project WHERE contractId=?";
            PreparedStatement statement = db.getDBConnection().prepareStatement(query);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            res.next();
            project = sqlSchemaToProject(res);
        } catch(Exception e) {
            System.out.println(e);
        }
        return project;
    }

    public static String addNewProject(JSONObject req) {
        // Tested - Works
        try{
            String query="INSERT into project (projectId, contractId, deadline, "+
                    "progressPercentage, projectName, projectDescription, projectStatus) "+
                    "values (null, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement=db.getDBConnection().prepareStatement(query);
            statement.setInt(1, req.getInt("contractId"));
            statement.setString(2, req.getString("deadline"));
            statement.setDouble(3, req.getDouble("progressPercentage"));
            statement.setString(4, req.getString("projectName"));
            statement.setString(5, req.getString("projectDescription"));
            statement.setString(6, req.getString("projectStatus"));
            statement.execute();
        }catch(Exception e){
            System.out.println(e);
            return "Failed";
        }
        return "Success";
    }

    public static Project sqlSchemaToProject(ResultSet res) throws SQLException {
        // Tested - Works
        if (res != null) {
            int projectId = res.getInt("projectId");
            int contractId = res.getInt("contractId");
            String deadline = res.getString("deadline");
            double progressPercentage = res.getDouble("progressPercentage");
            String projectName = res.getString("projectName");
            String projectDescription = res.getString("projectDescription");
            String projectStatus = res.getString("projectStatus");
            return new Project(projectId, contractId, deadline, progressPercentage,
                    projectName, projectDescription, projectStatus);
        }
        return null;
    }

    public static JSONObject sqlSchemaToJSON(ResultSet res) throws SQLException {
        JSONObject project = new JSONObject();
        project.put("projectId", res.getInt("projectId"));
        project.put("contractId", res.getInt("contractId"));
        project.put("deadline", res.getString("deadline"));
        project.put("progressPercentage", res.getDouble("progressPercentage"));
        project.put("projectName", res.getString("projectName"));
        project.put("projectDescription", res.getString("projectDescription"));
        project.put("projectStatus", res.getString("projectStatus"));
        return project;
    }

    public static void main(String[] args) {
        Project project = Project.getProjectWithId(1);
        System.out.println(project.projectDescription);

        Project project2 = Project.getProjectWithContractId(1);
        System.out.println(project2.projectName);

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
