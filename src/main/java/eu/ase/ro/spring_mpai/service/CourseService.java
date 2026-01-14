package eu.ase.ro.spring_mpai.service;

import eu.ase.ro.spring_mpai.model.Course;
import eu.ase.ro.spring_mpai.model.Student;
import eu.ase.ro.spring_mpai.repository.CourseRepository;
import eu.ase.ro.spring_mpai.request.CourseRequest;
import eu.ase.ro.spring_mpai.response.CourseResponse;
import eu.ase.ro.spring_mpai.response.StudentResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CourseService este clasa care conține logica de business pentru "cursuri".
 *
 * Ideea în arhitectura MVC:
 * - Controller-ul primește request-ul și se ocupă de navigare / input.
 * - Service-ul conține logica aplicației (creare, update, căutare, mapări).
 * - Repository-ul se ocupă de accesul la baza de date (CRUD).
 *
 * În plus, aici se face conversia între:
 * - Entity (Course, Student) -> obiecte persistate în DB
 * - DTO/Response (CourseResponse, StudentResponse) -> ce trimiți către view
 * - DTO/Request (CourseRequest) -> ce primești din formular
 */
@Service
public class CourseService {

    /**
     * CourseRepository este interfața care comunică cu DB pentru entitatea Course.
     * De obicei extinde JpaRepository<Course, Long> și îți dă findAll, findById, save etc.
     */
    private final CourseRepository courseRepository;

    /**
     * @PostConstruct rulează o singură dată, imediat după ce Spring a creat bean-ul
     * și i-a injectat dependențele (adică după constructor).
     *
     * Aici îl folosești ca "seed data" (date de test) ca să ai cursuri în aplicație
     * fără să le introduci manual.
     *
     * ATENȚIE practică:
     * - Dacă ai DB persistentă (nu in-memory), la fiecare restart poți ajunge să dublezi datele.
     * - Dacă ai H2 in-memory, e ok pentru demo.
     */
    @PostConstruct
    private void init() {
        // Creezi o listă fixă de cursuri (List.of => listă imutabilă)
        List<Course> courses = List.of(
                new Course(
                        "MPAI - Spring MVC",
                        "Mr T",
                        "totul despre modelul MVC implementat cu framekwork-ul Spring",
                        List.of(new Student("Popescu Ion", 23))
                ),
                new Course(
                        "Java fundamentals",
                        "Mr T",
                        "hai sa invatam Java",
                        List.of(
                                new Student("Ionescu Maria", 22),
                                new Student("Voicu Daniel", 22)
                        )
                )
        );

        // Salvează toate cursurile în DB dintr-un foc
        courseRepository.saveAll(courses);
    }

    /**
     * Constructor injection.
     *
     * Observație:
     * - În Spring modern, @Autowired pe constructor nu mai e necesar dacă ai un singur constructor,
     *   dar nu e greșit să-l lași.
     */
    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /**
     * Creează un curs nou folosind datele venite dintr-un formular (CourseRequest).
     *
     * Flux:
     * 1) primești request (title, trainer, description)
     * 2) construiești un entity Course
     * 3) îl salvezi în DB cu repository.save()
     *
     * Notă:
     * - aici nu setezi studenți; deci cursul nou va fi fără studenți inițial.
     */
    public void newCourse(CourseRequest request) {
        Course course = new Course(request.getTitle(), request.getTrainer(), request.getDescription());
        courseRepository.save(course);
    }

    /**
     * Update pentru un curs existent, identificat prin id.
     *
     * Flux:
     * 1) cauți cursul în DB
     * 2) dacă nu există -> arunci excepție (RuntimeException)
     * 3) dacă există -> îi actualizezi câmpurile
     * 4) save(course) persistă schimbările
     *
     * Important:
     * - findById(id).orElseThrow(...) => te asigură că lucrezi pe un obiect existent.
     * - save(course) la JPA poate funcționa și ca "update" dacă entitatea are ID.
     */
    public void updateById(Long id, CourseRequest request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("course not found"));

        // Aici actualizezi proprietățile entității cu valorile din request
        // Observă că request.getTitle() ajunge în course.setName() (title vs name - doar naming diferit)
        course.setName(request.getTitle());
        course.setTrainer(request.getTrainer());
        course.setDescription(request.getDescription());

        // Persistă modificările
        courseRepository.save(course);
    }

    /**
     * Returnează toate cursurile sub formă de CourseResponse (DTO pentru view).
     *
     * De ce nu returnezi direct List<Course>?
     * - Entity-urile pot avea relații (lazy loading), pot include câmpuri nedorite etc.
     * - DTO-ul (Response) e "curat": doar ce are nevoie view-ul.
     *
     * Stream:
     * - findAll() -> List<Course>
     * - map(this::toCourseResponse) -> transformă fiecare Course în CourseResponse
     * - toList() -> creează lista finală
     */
    public List<CourseResponse> getAll() {
        return courseRepository.findAll().stream()
                .map(this::toCourseResponse)
                .toList();
    }

    /**
     * Găsește un curs după id și îl returnează ca CourseResponse.
     *
     * map(this::toCourseResponse):
     * - dacă Optional<Course> are valoare -> o transformă în CourseResponse
     *
     * orElseThrow:
     * - dacă nu există -> aruncă excepție
     */
    public CourseResponse findById(Long id) {
        return courseRepository.findById(id)
                .map(this::toCourseResponse)
                .orElseThrow(() -> new RuntimeException("course not found"));
    }

    /**
     * Metodă internă (private) care transformă un entity Course într-un DTO CourseResponse.
     *
     * Ce se întâmplă aici:
     * - iei câmpurile simple (id, name, trainer, description)
     * - transformi lista de Student (entity) în lista de StudentResponse (DTO)
     *
     * Partea cu null:
     * - dacă course.getStudents() e null, întorci List.of() (listă goală),
     *   ca să eviți NullPointerException în view.
     */
    private CourseResponse toCourseResponse(Course course) {
        Long id = course.getId();
        String name = course.getName();
        String trainer = course.getTrainer();
        String description = course.getDescription();

        // Convertirea listei de studenți (Entity) -> (DTO)
        List<StudentResponse> students = course.getStudents() == null
                ? List.of()
                : course.getStudents().stream()
                .map(this::toStudentResponse)
                .toList();

        return new CourseResponse(id, name, trainer, description, students);
    }

    /**
     * Metodă internă (private) care transformă un entity Student într-un StudentResponse (DTO).
     *
     * Practic:
     * - extragi doar datele necesare pentru UI / view.
     */
    private StudentResponse toStudentResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getAge());
    }
}
