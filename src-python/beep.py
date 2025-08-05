import numpy as np
import soundfile as sf

# Parámetros del beep
duration = 0.1  # Duración en segundos
frequency = 1000  # Frecuencia en Hz
sample_rate = 44100  # Frecuencia de muestreo

# Generar la onda del beep (senoidal)
t = np.linspace(0, duration, int(sample_rate * duration), endpoint=False)
beep = 0.5 * np.sin(2 * np.pi * frequency * t)

# Guardar como archivo WAV
output_path = "beep.wav"
sf.write(output_path, beep, sample_rate)
print(f"Beep generado y guardado en: {output_path}")
# Nota: Este código genera un beep de 1000 Hz y lo guarda como un archivo WAV.
# Puedes ajustar la frecuencia y duración según tus necesidades.
