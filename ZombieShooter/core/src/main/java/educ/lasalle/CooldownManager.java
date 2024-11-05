package educ.lasalle;

public class CooldownManager {

    private static float shootCooldown = 1f;  // Temps de recharge initial (en secondes)
    private static float shootCooldownRemaining = 0f;  // Temps restant avant de pouvoir tirer
    private static float shootCooldownDecreaseRate = 0.05f;  // Vitesse à laquelle on diminue le cooldown
    private static float shootCooldownMin = 0.6f;  // Limite du cooldown (temps minimum entre chaque tir)
    public static boolean canShoot = false;

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
}
