package eu.ase.ro.spring_mpai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping; // <- import nefolosit (poate fi șters)

/**
 * HomeController este controller-ul responsabil de pagina principală (home).
 *
 * Rolul lui:
 * - să răspundă la request-uri GET pentru URL-urile "/" și "/home"
 * - să returneze view-ul "index"
 *
 * Este un controller MVC clasic (NU REST).
 */
@Controller
public class HomeController {

    /**
     * GET /
     *
     * Aceasta este ruta principală a aplicației.
     * Când accesezi în browser:
     *     http://localhost:8080/
     *
     * Ce se întâmplă:
     * - Spring vede @GetMapping("/")
     * - apelează metoda index()
     * - metoda returnează "index"
     * - Spring caută view-ul "index" (ex: index.html sau index.jsp)
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * GET /home
     *
     * Această rută face exact același lucru ca "/".
     * Practic ai două URL-uri diferite care duc la aceeași pagină.
     *
     * De ce ai face asta?
     * - pentru claritate semantică (/home)
     * - pentru compatibilitate sau redirect-uri viitoare
     *
     * Observație:
     * - Numele metodei (homee) NU contează pentru Spring.
     * - Contează doar adnotarea @GetMapping.
     */
    @GetMapping("/home")
    public String homee(){
        return "index";
    }
}
