nu ai aia in cod

„normalizați tag-urile ca listă de string-uri” înseamnă să faci tagurile așa: ["change", "deep-thoughts", "thinking", "world"]

Această bucată ia tag-urile din HTML și le transformă într-o listă de string-uri: tags = [ tag.get_text(strip=True) for tag in quote.select("a.tag") ]

Asta înseamnă „normalizare” în exercițiul tău: tag-urile nu rămân ca elemente HTML, ci sunt transformate într-o listă Python de string-uri.

De acum o sa raspund doar la mine
