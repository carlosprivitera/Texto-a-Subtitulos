import sys
from TTS.api import TTS
import librosa
import soundfile as sf
import os

# Argumentos esperados: texto y número de bloque
# Ejemplo de uso: python coqui_script.py "Texto de prueba" 001
text = sys.argv[1] if len(sys.argv) > 1 else "Este es un texto de prueba."
bloque = sys.argv[2] if len(sys.argv) > 2 else "000"

# Ruta de salida
nombre_archivo = f"{int(bloque):03d}_coqui.wav"
ruta_salida = os.path.join("salida-wav", nombre_archivo)

# Crear carpeta si no existe
os.makedirs("salida-wav", exist_ok=True)

# Cargar modelo español
model_name = "tts_models/es/css10/vits"
tts = TTS(model_name)

# Generar audio
wav = tts.tts(text)

# Convertir el audio a 44100 Hz
#wav_44100 = librosa.resample(wav, orig_sr=22050, target_sr=44100)

# Guardar
sf.write(ruta_salida, wav, samplerate=22050)
print(f"✅ Audio generado: {ruta_salida}")

