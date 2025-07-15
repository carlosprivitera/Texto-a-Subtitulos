#import sys
#from TTS.api import TTS
#import soundfile as sf

## Texto desde la línea de comandos
#text = sys.argv[1] if len(sys.argv) > 1 else "Este es un texto de prueba. Texto por defecto."

## Cargar modelo de español
#model_name = "tts_models/es/css10/vits"
#tts = TTS(model_name)

## Generar audio
#wav = tts.tts(text)

## Guardar resultado
#sf.write("salida-wav/coqui_output.wav", wav, samplerate=22050)
#print("✅ Audio generado en: salida-wav/coqui_output.wav ")

import sys
from TTS.api import TTS
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

# Guardar
sf.write(ruta_salida, wav, samplerate=22050)
print(f"✅ Audio generado: {ruta_salida}")

