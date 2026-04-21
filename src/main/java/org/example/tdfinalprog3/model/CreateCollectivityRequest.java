// CreateCollectivityRequest.java
package org.example.tdfinalprog3.model;

import java.util.List;

public class CreateCollectivityRequest {
    private String name;
    private String location;
    private String specialty;
    private List<String> members;
    private boolean federationApproval;
    private CreateCollectivityStructure structure;

    public static class CreateCollectivityStructure {
        private String president;
        private String vicePresident;
        private String treasurer;
        private String secretary;

        public String getPresident()
        { return president; }
        public void setPresident(String president)
        { this.president = president; }
        public String getVicePresident()
        { return vicePresident; }
        public void setVicePresident(String vicePresident)
        { this.vicePresident = vicePresident; }
        public String getTreasurer()
        { return treasurer; }
        public void setTreasurer(String treasurer)
        { this.treasurer = treasurer; }
        public String getSecretary()
        { return secretary; }
        public void setSecretary(String secretary)
        { this.secretary = secretary; }
    }

    public String getName()
    { return name; }
    public void setName(String name)
    { this.name = name; }
    public String getLocation()
    { return location; }
    public void setLocation(String location)
    { this.location = location; }
    public String getSpecialty()
    { return specialty; }
    public void setSpecialty(String specialty)
    { this.specialty = specialty; }
    public List<String> getMembers()
    { return members; }
    public void setMembers(List<String> members)
    { this.members = members; }
    public boolean isFederationApproval()
    { return federationApproval; }
    public void setFederationApproval(boolean federationApproval)
    { this.federationApproval = federationApproval; }
    public CreateCollectivityStructure getStructure()
    { return structure; }
    public void setStructure(CreateCollectivityStructure structure) { this.structure = structure; }
}