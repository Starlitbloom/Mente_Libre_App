import com.example.mente_libre_app.domain.validation.validateConfirm
import com.example.mente_libre_app.domain.validation.validateEmail
import com.example.mente_libre_app.domain.validation.validateNameLettersOnly
import com.example.mente_libre_app.domain.validation.validatePhoneDigitsOnly
import com.example.mente_libre_app.domain.validation.validateStrongPassword
import org.junit.Assert.*
import org.junit.Test

class ValidatorsTest {

    // ----------------- EMAIL -----------------
    @Test
    fun `empty email returns error`() {
        val result = validateEmail("")
        assertEquals("Email obligatorio", result)
    }

    @Test
    fun `invalid email returns error`() {
        val result = validateEmail("correo@invalido")
        assertEquals("Email inválido", result)
    }

    @Test
    fun `valid email returns null`() {
        val result = validateEmail("test@example.com")
        assertNull(result)
    }

    // ----------------- NOMBRE -----------------
    @Test
    fun `empty name returns error`() {
        val result = validateNameLettersOnly("")
        assertEquals("Apodo obligatorio", result)
    }

    @Test
    fun `name with numbers returns error`() {
        val result = validateNameLettersOnly("Juan123")
        assertEquals("Solo letras y espacios", result)
    }

    @Test
    fun `name with valid letters returns null`() {
        val result = validateNameLettersOnly("María José")
        assertNull(result)
    }

    // ----------------- TELÉFONO -----------------
    @Test
    fun `empty phone returns error`() {
        val result = validatePhoneDigitsOnly("")
        assertEquals("Teléfono obligatorio", result)
    }

    @Test
    fun `phone with letters returns error`() {
        val result = validatePhoneDigitsOnly("12345abc")
        assertEquals("Solo números", result)
    }

    @Test
    fun `phone too short returns error`() {
        val result = validatePhoneDigitsOnly("1234567")
        assertEquals("Debe tener entre 8 y 15 dígitos", result)
    }

    @Test
    fun `phone too long returns error`() {
        val result = validatePhoneDigitsOnly("1234567890123456")
        assertEquals("Debe tener entre 8 y 15 dígitos", result)
    }

    @Test
    fun `valid phone returns null`() {
        val result = validatePhoneDigitsOnly("987654321")
        assertNull(result)
    }

    // ----------------- CONTRASEÑA FUERTE -----------------
    @Test
    fun `empty password returns error`() {
        val result = validateStrongPassword("")
        assertEquals("Contraseña obligatoria", result)
    }

    @Test
    fun `short password returns error`() {
        val result = validateStrongPassword("Ab1!")
        assertEquals("Mínimo 8 caracteres", result)
    }

    @Test
    fun `password missing uppercase returns error`() {
        val result = validateStrongPassword("password1!")
        assertEquals("Debe incluir una mayúscula", result)
    }

    @Test
    fun `password missing lowercase returns error`() {
        val result = validateStrongPassword("PASSWORD1!")
        assertEquals("Debe incluir una minúscula", result)
    }

    @Test
    fun `password missing digit returns error`() {
        val result = validateStrongPassword("Password!")
        assertEquals("Debe incluir un número", result)
    }

    @Test
    fun `password missing symbol returns error`() {
        val result = validateStrongPassword("Password1")
        assertEquals("Debe incluir un símbolo", result)
    }

    @Test
    fun `password with spaces returns error`() {
        val result = validateStrongPassword("Password 1!")
        assertEquals("No debe contener espacios", result)
    }

    @Test
    fun `valid strong password returns null`() {
        val result = validateStrongPassword("Password1!")
        assertNull(result)
    }

    // ----------------- CONFIRMACIÓN -----------------
    @Test
    fun `confirm password blank returns error`() {
        val result = validateConfirm("Password1!", "")
        assertEquals("Confirma tu contraseña", result)
    }

    @Test
    fun `confirm password mismatch returns error`() {
        val result = validateConfirm("Password1!", "Password2!")
        assertEquals("Las contraseñas no coinciden", result)
    }

    @Test
    fun `confirm password match returns null`() {
        val result = validateConfirm("Password1!", "Password1!")
        assertNull(result)
    }
}