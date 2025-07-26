# src-python/mistral_prompt.py
import sys
import os
from transformers import AutoTokenizer, AutoModelForCausalLM, pipeline

def cargar_archivos(path_txt):
    base = os.path.splitext(path_txt)[0]
    path_prompt = os.path.join(os.path.dirname(path_txt), "prompt.txt") 
    path_salida = base + "-prompt.txt"

    //Imprimir por pantalla el nombre del archivo que contiene el texto a analizar
    print(f"📄 Archivo de entrada: {path_txt}")

    //Imprimir por pantalla el nombre del archivo que contiene el prompt a ejecutar por el modelo
    print(f"📄 Archivo de prompt: {path_prompt}")
  
    //Imprimir por pantalla el nombre del archivo de salida que contiene la respuesta
    print(f"📄 Archivo de salida: {path_salida}")
       
    with open(path_txt, "r", encoding="utf-8") as f: //Leer el archivo de texto a nalizar
        contenido = f.read()

    with open(path_prompt, "r", encoding="utf-8") as f: //Leer el archivo de prompt para ejecutar por el modelo
        prompt = f.read()

    return prompt + "\n\n" + contenido, path_salida

def generar_respuesta(entrada, path_salida):
    print("🧠 Cargando modelo Mistral...")
    modelo_id = "mistralai/Mistral-7B-Instruct-v0.2"
    tokenizer = AutoTokenizer.from_pretrained(modelo_id)
    model = AutoModelForCausalLM.from_pretrained(modelo_id, device_map="auto")

    pipe = pipeline("text-generation", model=model, tokenizer=tokenizer)

    print("🤖 Generando prompts...")
    respuesta = pipe(entrada, max_new_tokens=1024, do_sample=True, temperature=0.7)[0]["generated_text"]

    with open(path_salida, "w", encoding="utf-8") as f:
        f.write(respuesta)

    print(f"✅ Respuesta guardada en: {path_salida}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("❌ Uso: python mistral_prompt.py nombre_archivo.txt")
        sys.exit(1)

    texto, salida = cargar_archivos(sys.argv[1]) //Carga el texto y el prompt, parámetro 0 es el nombre del archivo de texto y parámetro 1 es el nombre del archivo prompt
    generar_respuesta(texto, salida)
    