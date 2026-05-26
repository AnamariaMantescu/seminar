de ce e normalizat la 1? care e bucata care o face 

În Exercițiul 1 cu cărțile, disponibilitatea este „normalizată la 1” pentru că cerința spunea: disponibilitatea: 1 pentru "In stock", 0 altfel

Bucata care face asta este:
availability_text = book.select_one(".availability").get_text(strip=True)
availability = 1 if "In stock" in availability_text else 0

Mai exact, importanta este:
availability = 1 if "In stock" in availability_text else 0
