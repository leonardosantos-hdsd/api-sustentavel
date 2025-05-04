set -e  # Encerra o script ao primeiro erro

echo "==== CLONANDO REPOSITÓRIO ===="
git clone https://github.com/leonardosantos-hdsd/api-sustentavel.git
cd api-sustentavel

echo "==== CRIANDO OU ACESSANDO A BRANCH feature/security ===="
git checkout -b feature/security || git checkout feature/security

echo "==== (SIMULADO) DESENVOLVENDO NA BRANCH ===="
# Simule modificações se desejar:
# echo "// novo código" >> src/NovaClasse.java

echo "==== COMMITANDO ALTERAÇÕES ===="
git add .
git commit -m "feat(security): implementação da feature de segurança"
git push origin feature/security

echo "==== GARANTINDO EXISTÊNCIA DA BRANCH dev ===="
git checkout -b dev || git checkout dev
git push origin dev

echo "==== MERGE DE feature/security → dev ===="
git merge feature/security
git push origin dev

echo "==== MERGE DE dev → main ===="
git checkout main
git merge dev
git push origin main

echo "==== CRIANDO E ENVIANDO TAG v1.0.1 ===="
git tag v1.0.1
git push origin v1.0.1

echo "✅ FLUXO FINALIZADO COM SUCESSO!"
