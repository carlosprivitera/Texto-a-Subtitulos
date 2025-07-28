# src-python/mistral_responder.py
import sys
from transformers import AutoTokenizer, AutoModelForCausalLM, pipeline

def main():
    if len(sys.argv) < 2:
        print("❌ Error: se espera el texto como argumento.")
        sys.exit(1)

    entrada = sys.argv[1]

    print("🧠 Cargando modelo Mistral...", flush=True)
    modelo_id = "mistralai/Mistral-7B-Instruct-v0.2"
    tokenizer = AutoTokenizer.from_pretrained(modelo_id)
    model = AutoModelForCausalLM.from_pretrained(modelo_id, device_map="auto")

    pipe = pipeline("text-generation", model=model, tokenizer=tokenizer)

    print("🤖 Generando respuesta...", flush=True)
    respuesta = pipe(entrada, max_new_tokens=1024, do_sample=True, temperature=0.7)[0]["generated_text"]

    print("✅ Respuesta generada:\n", flush=True)
    print(respuesta)

if __name__ == "__main__":
    main()
    
    