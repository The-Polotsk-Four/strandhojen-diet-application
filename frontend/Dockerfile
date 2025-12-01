# Smallest nginx base image
FROM nginx:alpine

#Copy everything in your frontend folder into Nginx HTML directory
COPY . /usr/share/nginx/html

#Expose port 80
EXPOSE 80

#-g and daemon off keeps nginx in the foreground alowwing proper tracking
#will close when opened if these are missing
CMD ["nginx", "-g", "daemon off;"]