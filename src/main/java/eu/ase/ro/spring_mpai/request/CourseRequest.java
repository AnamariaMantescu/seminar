package eu.ase.ro.spring_mpai.request;

/**
 * CourseRequest este un DTO folosit pentru a primi datele
 * din formularul HTML (ADD / EDIT course).
 *
 * Acest obiect este populat automat de Spring prin @ModelAttribute.
 */
public class CourseRequest {

    // Titlul cursului – vine din input-ul HTML cu name="title"
    private String title;

    // Numele trainerului – vine din input-ul HTML cu name="trainer"
    private String trainer;

    // Descrierea cursului – vine din input-ul HTML cu name="description"
    private String description;

    /**
     * Constructor gol
     * OBLIGATORIU pentru Spring MVC (data binding).
     */
    public CourseRequest() {
    }

    /**
     * Constructor complet
     * Util pentru teste sau creare manuală.
     */
    public CourseRequest(String title, String trainer, String description) {
        this.title = title;
        this.trainer = trainer;
        this.description = description;
    }

    // Getter pentru title
    public String getTitle() {
        return title;
    }

    // Setter pentru title – Spring îl apelează automat
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter pentru trainer
    public String getTrainer() {
        return trainer;
    }

    // Setter pentru trainer
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }

    // Getter pentru description
    public String getDescription() {
        return description;
    }

    // Setter pentru description
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * toString()
     * Folosit pentru debugging (ex: System.out.println(request))
     */
    @Override
    public String toString() {
        return "CourseRequest{" +
                "title='" + title + '\'' +
                ", trainer='" + trainer + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
