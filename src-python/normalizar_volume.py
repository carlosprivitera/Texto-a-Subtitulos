import subprocess
import sys
import os

# Argumento: número de bloque
# Ejemplo de uso: python normalizar_volume.py 001
bloque = sys.argv[1] if len(sys.argv) > 1 else "000"
nombre_base = f"{int(bloque):03d}"
archivo_entrada = os.path.join("salida-wav", f"{nombre_base}_coqui.wav")
archivo_salida = os.path.join("salida-wav", f"{nombre_base}_normalized_output.wav")

# Verificar existencia del archivo de entrada
if not os.path.exists(archivo_entrada):
    print(f"❌ El archivo de entrada no existe: {archivo_entrada}")
    sys.exit(1)

# Comando FFmpeg
cmd = [
    "ffmpeg", "-y",
    "-i", archivo_entrada,
    "-af", "loudnorm=I=-16:TP=-1.5:LRA=11",
    archivo_salida
]

print(f"🔊 Normalizando volumen de: {archivo_entrada}")
#subprocess.run(cmd)
#result = subprocess.run(cmd, capture_output=True, text=True)
try:
    # Usar subprocess.run con manejo explícito de recursos
    result = subprocess.run(cmd, capture_output=True, text=True, check=True)
    print(result.stdout)
except subprocess.CalledProcessError as e:
    print(f"❌ Error al ejecutar FFmpeg: {e.stderr}")
    sys.exit(1)
    
# Verificar salida de FFmpeg
if result.returncode != 0:
    print(f"❌ Error al ejecutar FFmpeg: {result.stderr}")
    sys.exit(1)

#print(f"✅ Volumen normalizado en: {archivo_salida}")
# Verificar si el archivo de salida fue modificado
if os.path.exists(archivo_salida):
    if os.path.getsize(archivo_salida) > 0:
        print(f"✅ Volumen normalizado en: {archivo_salida}")
    else:
        print(f"❌ El archivo de salida está vacío: {archivo_salida}")
else:
    print(f"❌ El archivo de salida no fue creado: {archivo_salida}")
    
