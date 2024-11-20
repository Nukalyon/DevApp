package educ.lasalle.manager;

public class CooldownManager {

    // Temps de recharge initial (en secondes)
    private static float shootCooldown = 1f;
    // Temps restant avant de pouvoir tirer
    private static float shootCooldownRemaining = 0f;
    // Vitesse à laquelle on diminue le cooldown
    private static float shootCooldownDecreaseRate = 0.05f;
    // Limite du cooldown (temps minimum entre chaque tir)
    private static float shootCooldownMin = 0.6f;
    public static boolean canShoot = false;

    // Temps d'attente avant de pouvoir appuyer sur Échap à nouveau
    private static float escapeCooldownRemaining = 0f;
    private static float escapeCooldownDecreaseRate = 0.05f;
    // Temps en secondes (ajustez ce délai selon vos besoins)
    private static final float ESCAPE_COOLDOWN_TIME = 5.0f;
    public static boolean canPress = true;


    public static void updateCooldownBullet(float delta) {
        if (shootCooldown > shootCooldownMin) {
            shootCooldown -= shootCooldownDecreaseRate * delta;
            if (shootCooldown < shootCooldownMin) {
                shootCooldown = shootCooldownMin;
            }
        }
        // Réduire le cooldown restant
        shootCooldownRemaining -= delta;
        // Si le cooldown est terminé, tirer
        if (shootCooldownRemaining <= 0f) {
            canShoot = true;
            shootCooldownRemaining = shootCooldown;  // Reset du temps restant
        }
        else {
            canShoot = false;
        }
    }

    // Met à jour le cooldown de la touche Échap
    public static void updateCooldownEscape(float delta) {
        if (escapeCooldownRemaining > 0f) {
            canPress = false;
            escapeCooldownRemaining -= escapeCooldownDecreaseRate * delta;  // Réduire le cooldown
            if (escapeCooldownRemaining <= 0f) {
                canPress = true;  // Permet à la touche Échap d'être pressée à nouveau
            }
        }
    }

    // Appelée dans GameScreen ou ailleurs pour savoir si l'on peut appuyer sur Échap
    public static void tryPressEscape() {
        if (canPress) {
            canPress = false;  // Empêche de presser Échap immédiatement à nouveau
            escapeCooldownRemaining = ESCAPE_COOLDOWN_TIME;  // Réinitialise le cooldown
        }
    }

    public static boolean canPressEscape() {
        return canPress;
    }
}
