package org.example.tdfinalprog3.model;

import java.time.LocalDate;
import java.util.List;

public class CollectivityResponse {

    private String id;
    private String number;
    private String name;
    private String location;
    private String specialty;
    private LocalDate creationDate;
    private boolean federationApproval;
    private CollectivityStructureResponse structure;
    private List<Member> members;

    public CollectivityResponse() {}
    

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public LocalDate getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDate creationDate) { this.creationDate = creationDate; }

    public boolean isFederationApproval() { return federationApproval; }
    public void setFederationApproval(boolean federationApproval) { this.federationApproval = federationApproval; }

    public CollectivityStructureResponse getStructure() { return structure; }
    public void setStructure(CollectivityStructureResponse structure) { this.structure = structure; }

    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }

    public static class CollectivityStructureResponse {
        private Member president;
        private Member vicePresident;
        private Member treasurer;
        private Member secretary;

        public Member getPresident() { return president; }
        public void setPresident(Member president) { this.president = president; }

        public Member getVicePresident() { return vicePresident; }
        public void setVicePresident(Member vicePresident) { this.vicePresident = vicePresident; }

        public Member getTreasurer() { return treasurer; }
        public void setTreasurer(Member treasurer) { this.treasurer = treasurer; }

        public Member getSecretary() { return secretary; }
        public void setSecretary(Member secretary) { this.secretary = secretary; }
    }
}
