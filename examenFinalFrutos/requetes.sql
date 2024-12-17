-- Requêtes SQL Examen Final Automne 2024
-- Recherche d'un abonné par identifiant
SELECT * FROM Abonne WHERE idAbonne = 1;

-- Recherche d'un abonné par nom
SELECT * FROM Abonne WHERE nom = 'Durand';

-- Recherche des livres disponibles
SELECT * FROM Livre WHERE disponible = TRUE;

-- Afficher tous les emprunts en cours (livres empruntés et abonnés)
SELECT e.idEmprunt, a.nom, a.prenom, l.titre, e.date_emprunt, e.date_retour
FROM Emprunt e
JOIN Abonne a ON e.idAbonne = a.idAbonne
JOIN Livre l ON e.idLivre = l.idLivre
WHERE e.date_retour >= CURDATE();

-- Afficher l'historique des emprunts d'un abonné
SELECT e.idEmprunt, l.titre, e.date_emprunt, e.date_retour
FROM Emprunt e
JOIN Livre l ON e.idLivre = l.idLivre
WHERE e.idAbonne = 1;

-- Mise à jour de la disponibilité d'un livre (par exemple, un livre qui est retourné)
UPDATE Livre
SET disponible = TRUE
WHERE idLivre = 1;

-- Suppression d'un abonné (lorsqu'un abonné est supprimé, ses emprunts doivent également l'être)
DELETE FROM Abonne WHERE idAbonne = 1;

-- Suppression d'un livre (lorsqu'un livre est supprimé, ses emprunts doivent également l'être)
DELETE FROM Livre WHERE idLivre = 1;

