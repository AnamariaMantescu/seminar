package eu.ase.ro.spring_mpai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * StudentController este responsabil de paginile (views) legate de studenți.
 *
 * Rolul lui:
 * - să gestioneze navigarea către paginile de listare, adăugare și editare studenți
 * - momentan NU face operații de business (save/update),
 *   ci doar returnează view-uri
 */
@Controller
public class StudentController {

    /**
     * GET /students
     *
     * Scop:
     * - Deschide pagina cu lista de studenți.
     *
     * Ce se întâmplă:
     * - Când accesezi /students în browser,
     *   Spring apelează această metodă
     * - Returnează view-ul "students/index"
     *
     * Observație:
     * - Momentan nu trimiți date în Model (listă de studenți).
     * - Probabil urmează să adaugi un StudentService și un Model aici.
     */
    @GetMapping("/students")
    public String navigateToStudentsPage() {
        return "students/index";
    }

    /**
     * GET /students/add
     *
     * Scop:
     * - Deschide pagina pentru adăugarea unui student nou.
     *
     * Detaliu important:
     * - Folosești view-ul "students/edit" și pentru ADD și pentru EDIT,
     *   exact ca la CourseController.
     * - Într-o variantă completă, aici ai trimite în Model
     *   un obiect StudentResponse gol.
     */
    @GetMapping("/students/add")
    public String navigateToAddStudentPage() {
        return "students/edit";
    }

    /**
     * GET /students/edit
     *
     * Scop:
     * - Deschide pagina de editare a unui student existent.
     *
     * Observație IMPORTANTĂ:
     * - Ruta NU conține un id (ex: /students/{id}/edit).
     * - Asta înseamnă că momentan nu poți edita un student specific.
     *
     * De obicei, forma corectă ar fi:
     *   @GetMapping("/students/{id}/edit")
     *   cu @PathVariable Long id
     *
     * În stadiul actual:
     * - metoda doar afișează pagina "students/edit"
     * - nu știe ce student trebuie editat
     */
    @GetMapping("/students/edit")
    public String navigateToEditStudentPage() {
        return "students/edit";
    }
}
