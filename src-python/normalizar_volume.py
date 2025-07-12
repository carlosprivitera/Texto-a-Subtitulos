import subprocess
import sys

# Ruta del archivo de entrada
input_path = sys.argv[1] if len(sys.argv) > 1 else "coqui_output.wav"
output_path = "normalized_output.wav"

# Comando FFmpeg para normalización EBU R128 (loudnorm)
cmd = [
    "ffmpeg", "-y",
    "-i", input_path,
    "-af", "loudnorm=I=-16:TP=-1.5:LRA=11",
    output_path
]

print(f"🔊 Normalizando volumen de: {input_path}")
subprocess.run(cmd)
print(f"✅ Volumen normalizado en: {output_path}")
