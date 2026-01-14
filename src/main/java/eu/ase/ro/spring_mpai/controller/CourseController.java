package eu.ase.ro.spring_mpai.controller;

import eu.ase.ro.spring_mpai.request.CourseRequest;
import eu.ase.ro.spring_mpai.response.CourseResponse;
import eu.ase.ro.spring_mpai.response.StudentResponse; // <- import nefolosit în acest controller (poate îl vei folosi mai târziu)
import eu.ase.ro.spring_mpai.service.CourseService;
import jakarta.annotation.PostConstruct; // <- import nefolosit aici (dacă nu ai o metodă @PostConstruct, îl poți șterge)
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList; // <- import nefolosit
import java.util.List;

/**
 * CourseController este un controller Spring MVC care gestionează paginile (views) legate de cursuri.
 *
 * IMPORTANT:
 * - @Controller înseamnă că metodele returnează de obicei numele unui view (ex: "courses/index"),
 *   NU JSON. (Pentru JSON ai folosi @RestController sau @ResponseBody.)
 * - Controller-ul primește request-uri HTTP (GET/POST), apelează service-ul,
 *   pune date în Model, apoi întoarce view-ul (pagina) care va fi randată.
 */
@Controller
public class CourseController {

    /**
     * CourseService conține logica de business pentru cursuri.
     * Controller-ul ar trebui să fie "subțire": doar primește request-ul, cheamă service-ul,
     * pregătește datele pentru view și returnează pagina.
     *
     * 'final' => dependența nu se schimbă după construirea controller-ului.
     */
    private final CourseService courseService;

    /**
     * Constructor Injection (cea mai recomandată variantă în Spring):
     * - Spring îți injectează automat un bean de CourseService.
     * - Avantaje: testare mai ușoară, dependențe clare, imutabilitate.
     */
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * GET /courses
     *
     * Scop:
     * - Afișează lista tuturor cursurilor.
     *
     * Ce se întâmplă:
     * 1) Se apelează courseService.getAll() pentru a lua lista de cursuri (CourseResponse).
     * 2) Se pune lista în Model sub cheia "courses".
     *    Model-ul e ca un "bag" de date pe care îl primește view-ul (Thymeleaf/JSP etc.).
     * 3) Se returnează "courses/index" -> Spring caută template-ul/pagina courses/index.
     */
    @GetMapping("/courses")
    public String navigateToCoursesPage(Model model) {
        List<CourseResponse> courses = courseService.getAll();
        model.addAttribute("courses", courses);
        return "courses/index";
    }

    /**
     * GET /courses/{id}/edit
     *
     * Scop:
     * - Deschide pagina de editare pentru un curs existent.
     *
     * Ce e important:
     * - @PathVariable Long id ia valoarea din URL (ex: /courses/5/edit -> id = 5)
     *
     * Flux:
     * 1) courseService.findById(id) ia datele cursului (CourseResponse).
     * 2) Pune cursul în Model sub cheia "course".
     * 3) Returnează view-ul "courses/edit" unde formularul va fi pre-populat cu datele cursului.
     */
    @GetMapping("/courses/{id}/edit")
    public String navigateToEditCoursePage(@PathVariable Long id, Model model) {
        CourseResponse response = courseService.findById(id);
        model.addAttribute("course", response);

        return "courses/edit";
    }

    /**
     * GET /courses/add
     *
     * Scop:
     * - Deschide pagina de adăugare a unui curs nou.
     *
     * Observație:
     * - Folosești același view "courses/edit" și pentru add și pentru edit.
     *   Diferența e că aici trimiți un obiect gol (new CourseResponse()) ca să aibă formularul
     *   un obiect de "binding" pe care să-l poată completa.
     *
     * În practică:
     * - View-ul va afișa câmpuri goale, iar la submit vei salva un curs nou.
     */
    @GetMapping("/courses/add")
    public String navigateToAddCoursePage(Model model) {
        model.addAttribute("course", new CourseResponse());
        return "courses/edit";
    }

    /**
     * GET /courses/students/enroll
     *
     * Scop:
     * - Deschide pagina unde probabil înscrii un student la un curs.
     *
     * Notă:
     * - Momentan nu trimiți nimic în Model (nici listă de studenți/cursuri).
     *   Dacă vei avea dropdown-uri (student list / course list), cel mai probabil va trebui
     *   să pui în model acele liste aici.
     */
    @GetMapping("/courses/students/enroll")
    public String navigateToEnrollStudentPage() {
        return "courses/enroll";
    }

    /**
     * POST /courses/save
     *
     * Scop:
     * - Primește datele din formular (add/edit) și le salvează.
     *
     * Parametri:
     * - @ModelAttribute CourseRequest request:
     *   Spring "leagă" automat câmpurile din formular (name, description etc.)
     *   într-un obiect CourseRequest (trebuie să aibă getter/setter).
     *
     * - @RequestParam(required = false) Long courseId:
     *   Ia un parametru din query/body cu numele courseId (ex: courseId=5).
     *   required=false => dacă nu vine (în cazul "add"), va fi null.
     *
     * Logica:
     * - Dacă courseId == null -> înseamnă că e curs nou -> courseService.newCourse(request)
     * - Altfel -> update la cursul existent -> courseService.updateById(courseId, request)
     *
     * Return:
     * - "redirect:/courses" -> nu returnează un view direct,
     *   ci spune browser-ului să facă un nou GET la /courses.
     *   Avantaj: previne problema de "resubmit" la refresh (PRG pattern: Post/Redirect/Get).
     */
    @PostMapping("/courses/save")
    public String save(@ModelAttribute CourseRequest request,
                       @RequestParam(required = false) Long courseId) {

        // Debug: afișare în consolă. În aplicații reale, se preferă logger (ex: slf4j).
        System.out.println("Course Id received: " + courseId);
        System.out.println("Course has been saved: " + request.toString());

        // Decide dacă e CREATE sau UPDATE în funcție de prezența lui courseId
        if (courseId == null) {
            courseService.newCourse(request);
        } else {
            courseService.updateById(courseId, request);
        }

        // Redirect la listă după salvare
        return "redirect:/courses";
    }
}
