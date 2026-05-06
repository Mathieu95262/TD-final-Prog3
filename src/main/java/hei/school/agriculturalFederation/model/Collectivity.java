package hei.school.agriculturalFederation.model;

import java.util.List;

public class Collectivity {
    private String id;
    private String uniqueNumber;
    private String uniqueName;
    private String name;
    private String location;
    private String agriculturalSpecialty;
    private CollectivityStructure structure;
    private List<Member> members;

    public Collectivity() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUniqueNumber() { return uniqueNumber; }
    public void setUniqueNumber(String uniqueNumber) { this.uniqueNumber = uniqueNumber; }

    public String getUniqueName() { return uniqueName; }
    public void setUniqueName(String uniqueName) { this.uniqueName = uniqueName; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAgriculturalSpecialty() { return agriculturalSpecialty; }
    public void setAgriculturalSpecialty(String agriculturalSpecialty) {
        this.agriculturalSpecialty = agriculturalSpecialty;
    }

    public CollectivityStructure getStructure() { return structure; }
    public void setStructure(CollectivityStructure structure) { this.structure = structure; }

    public List<Member> getMembers() { return members; }
    public void setMembers(List<Member> members) { this.members = members; }
}
