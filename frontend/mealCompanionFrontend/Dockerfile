# frontend/Dockerfile (dev) - uses Node 20 for Vite compatibility
FROM node:20-bullseye

WORKDIR /app

# copy package files first for cache
COPY package.json package-lock.json ./

# install deps
RUN npm ci

# copy source
COPY . .

# expose Vite dev server port and ensure it binds to 0.0.0.0
ENV HOST=0.0.0.0
ENV PORT=5173

EXPOSE 5173

# Use Vite dev server; ensure script in package.json is "dev": "vite"
CMD ["npm", "run", "dev", "--", "--host", "0.0.0.0"]


