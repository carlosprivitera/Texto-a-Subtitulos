# generar_imagen_sdxl.py
import sys
import os
import torch
from diffusers import StableDiffusionXLPipeline

def obtener_parametros():
    if len(sys.argv) < 2:
        print("Uso: python generar_imagen_sdxl.py \"prompt\" [height] [width] [guidance_scale] [num_inference_steps] [output_file]")
        sys.exit(1)

    prompt = sys.argv[1]
    try:
        height = int(sys.argv[2]) if len(sys.argv) > 2 else 1080
        width = int(sys.argv[3]) if len(sys.argv) > 3 else 1920
        guidance_scale = float(sys.argv[4]) if len(sys.argv) > 4 else 7.5
        num_steps = int(sys.argv[5]) if len(sys.argv) > 5 else 40
    except ValueError:
        print("❌ Error: height, width, guidance_scale y num_inference_steps deben ser valores numéricos.")
        sys.exit(1)

    # Validaciones de rango
    if not (512 <= height <= 2048):
        print("❌ Error: height debe estar entre 512 y 2048.")
        sys.exit(1)
    if not (512 <= width <= 2048):
        print("❌ Error: width debe estar entre 512 y 2048.")
        sys.exit(1)
    if not (1.0 <= guidance_scale <= 20.0):
        print("❌ Error: guidance_scale debe estar entre 1.0 y 20.0.")
        sys.exit(1)
    if not (10 <= num_steps <= 100):
        print("❌ Error: num_inference_steps debe estar entre 10 y 100.")
        sys.exit(1)

    # Nombre de archivo de salida
    output_file = sys.argv[6] if len(sys.argv) > 6 else "salida-imagenes/imagen_generada.png"
    if not output_file.lower().endswith(('.png', '.jpg', '.jpeg')):
        print("❌ Error: el nombre del archivo de salida debe terminar en .png, .jpg o .jpeg")
        sys.exit(1)

    # Asegurar carpeta de salida
    os.makedirs(os.path.dirname(output_file), exist_ok=True)

    return prompt, height, width, guidance_scale, num_steps, output_file

def generar_imagen(prompt, height, width, guidance_scale, num_steps, output_file):
    print("✅ Cargando modelo, esperar...")
    pipe = StableDiffusionXLPipeline.from_pretrained(
        "stabilityai/stable-diffusion-xl-base-1.0",
        torch_dtype=torch.float32,
        use_safetensors=True
    )
    pipe.to("cpu")

    print(f"🖼️ Generando imagen con resolución {width}x{height} guidance_scale={guidance_scale} num_inference_steps={num_steps}\n")
    image = pipe(prompt, height=height, width=width,
                 guidance_scale=guidance_scale,
                 num_inference_steps=num_steps).images[0]

    image.save(output_file)
    print(f"✅ Imagen generada: {output_file}")

if __name__ == "__main__":
    prompt, height, width, guidance_scale, num_steps, output_file = obtener_parametros()
    generar_imagen(prompt, height, width, guidance_scale, num_steps, output_file)
    
## generar_imagen_sdxl.py
#import torch
#from diffusers import StableDiffusionXLPipeline

## Cargar pipeline para CPU
#pipe = StableDiffusionXLPipeline.from_pretrained(
#    "stabilityai/stable-diffusion-xl-base-1.0", 
#    torch_dtype=torch.float32,
#    use_safetensors=True
#)
#pipe.to("cpu")

## Texto a convertir (puede ser pasado por CLI)
#print("✅ Creando imagen, esperar, puede demorar ... ")
#prompt = "Un robot futurista leyendo un libro en una biblioteca oscura, estilo realista"
#image = pipe(prompt, height=1080, width=1920, guidance_scale=7.5, num_inference_steps=40).images[0]
#print("✅ imagen creada con éxito.")
## Guardar imagen
#image.save("salida-imagenes/imagen_generada.png")
#print("✅ Imagen generada: salida-imagenes/imagen_generada.png")
