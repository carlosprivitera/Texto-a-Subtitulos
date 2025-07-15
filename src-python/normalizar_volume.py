#import subprocess
#import sys

## Ruta del archivo de entrada
#input_path = sys.argv[1] if len(sys.argv) > 1 else "salida-wav/coqui_output.wav"
#output_path = "salida-wav/normalized_output.wav"

## Comando FFmpeg para normalización EBU R128 (loudnorm)
## ···············
#cmd = [
#    "ffmpeg", "-y",
#    "-i", input_path,
#    "-af", "loudnorm=I=-16:TP=-1.5:LRA=11",
#    output_path
#]

#print(f"🔊 Normalizando volumen de: {input_path}")
#subprocess.run(cmd)
#print(f"✅ Volumen normalizado en: {output_path}")


import subprocess
import sys
import os

# Argumento: número de bloque
# Ejemplo de uso: python normalizar_volume.py 001
bloque = sys.argv[1] if len(sys.argv) > 1 else "000"
nombre_base = f"{int(bloque):03d}"
archivo_entrada = os.path.join("salida-wav", f"{nombre_base}_coqui.wav")
archivo_salida = os.path.join("salida-wav", f"{nombre_base}_normalized_output.wav")

# Comando FFmpeg
cmd = [
    "ffmpeg", "-y",
    "-i", archivo_entrada,
    "-af", "loudnorm=I=-16:TP=-1.5:LRA=11",
    archivo_salida
]

print(f"🔊 Normalizando volumen de: {archivo_entrada}")
subprocess.run(cmd)
print(f"✅ Volumen normalizado en: {archivo_salida}")

