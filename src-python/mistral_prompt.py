# src-python/mistral_prompt.py
import sys
import os

# ⚠️ Asegurarse de que la GPU no se use (opcional pero recomendado)
os.environ["CUDA_VISIBLE_DEVICES"] = ""

from transformers import AutoTokenizer, AutoModelForCausalLM, pipeline

def main():
    if len(sys.argv) < 2:
        print("❌ Error: se espera el texto como argumento.")
        sys.exit(1)

    entrada = sys.argv[1]

    print("🧠 Cargando modelo Mistral...", flush=True)
    modelo_id = "mistralai/Mistral-7B-Instruct-v0.2"
    tokenizer = AutoTokenizer.from_pretrained(modelo_id)

    # 🔧 Forzar carga del modelo en CPU
    model = AutoModelForCausalLM.from_pretrained(modelo_id, device_map={"": "cpu"})

    pipe = pipeline("text-generation", model=model, tokenizer=tokenizer)

    print("🤖 Generando respuesta...", flush=True)
    #//Los parámetros significan:
    #//max_new_tokens: número máximo de tokens a generar, el rango mínimo es 1 y el máximo es 4096.
    #//do_sample: si se debe muestrear de la distribución de probabilidad
    #//temperature: controla la aleatoriedad de la generación (0.7 es un valor común),
    #// valores más bajos hacen que la salida sea más predecible, mientras que valores más altos la hacen más creativa.
    #//Otros parámetros como top_k y top_p también se pueden ajustar para controlar la diversidad de la salida.
    #//top_k: número de palabras más probables a considerar en cada paso de generación. Los valores más bajos hacen que la salida sea más predecible.
    #// el valor mínimo es 1 y el máximo es 100. El valor por defecto es 50.
    #//top_k: es una técnica de muestreo que limita el número de palabras candidatas a las más probables,
    #// los valores más altos permiten más diversidad. El valor mínimo es 1 y el máximo es 100. El valor por defecto es 50. 
    respuesta = pipe(entrada, max_new_tokens=1024, do_sample=True, temperature=0.7)[0]["generated_text"]

    print("✅ Respuesta generada:\n", flush=True)
    print(respuesta)

if __name__ == "__main__":
    main()
    
    
    