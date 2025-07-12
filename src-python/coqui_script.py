import sys
from TTS.api import TTS
import soundfile as sf

# Texto desde la línea de comandos
text = sys.argv[1] if len(sys.argv) > 1 else "Este es un texto de prueba. Text por defecto."

# Cargar modelo de español
model_name = "tts_models/es/css10/vits"
tts = TTS(model_name)

# Generar audio
wav = tts.tts(text)

# Guardar resultado
sf.write("coqui_output.wav", wav, samplerate=22050)
print("✅ Audio generado en 'coqui_output.wav'")
