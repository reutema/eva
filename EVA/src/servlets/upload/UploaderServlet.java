package servlets.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Upload")
// @MultipartConfig
public class UploaderServlet extends HttpServlet
{

    private final static String FILENAME = "filename";

    private final static String UPLOAD = "upload";

    private FileArchive files;

    @Override
    public void init() throws ServletException
    {
        this.files = new FileArchive();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        // TODO shoo download button and list of hyperlinks to file with delete
        // button

        PrintWriter out = response.getWriter();

        out.println("<html>\n<head>");
        out.println("<h1>Upload</h1>");
        out.println("</head>\n<body>");
        // out.println("<form method=\"post\" enctype=\"multipart/form-data\"
        // action=\"Upload\">");
        // out.println("Dateiname: <input name=\"" + FILENAME + "\"
        // type=\"text\"><br>");
        // out.println("Datei: <input name=\"upload\" type=\"file\"><br>");
        // out.println("<input type=\"submit\" value=\"Jetzt hochladen!\">");
        // out.println("</form>");

        out.println("<form method=\"post\" action=\"Upload\"enctype=\"multipart/form-data\">");
        out.println("<p>Dateiname: <input type=\"text\" name=\"" + FILENAME + "\"><br></p>");
        out.println("<p>Datei: <input name=\"" + UPLOAD + "\"type=\"file\"><br></p>");
        out.println("<input type=\"submit\" value=\"Jetzt hochladen!\">");
        out.println("</form>");
        out.println(files.getListOfFiles());

        // out.println("<form action=\"Upload\" method=\"post\"
        // enctype=\"multipart/form-data\">");
        // out.println("Name: <input type=\"text\" name=\"username\" />");
        // out.println("User Images: <input type=\"file\" name=\"user_image\"
        // />");
        // out.println("<input type=\"submit\" value=\"Submit\" />");
        // out.println("</form>");

        out.println("</body>\n</html>");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        // TODO read file from request and save it, great new hyperlink and show
        // get

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html>\n<header>");
        out.println("Upload");
        out.println("</header>\n<body>");

        String fileName = request.getParameter(FILENAME);
        String upld = request.getParameter(UPLOAD);
        String username = request.getParameter("username");
        String user = request.getParameter("user_image");
        // System.out.println(fileName);
        // System.out.println(upld);
        // out.println("<p>request.getParameter()\"filename\" = " + fileName +
        // "</p>");
        // out.println("<p>request.getParameter()\"upload\" = " + upld +
        // "</p>");
        // out.println("<p>request.getParameter()\"username\" = " + username +
        // "</p>");
        // out.println("<p>request.getParameter()\"user\" = " + user + "</p>");
        // out.println("<p>reader.readLine()</p>");

        BufferedReader reader = request.getReader();
        // FileWriter writer = new FileWriter(fileName);

        // upload

        out.println("<pre>");
        // BufferedReader reader = request.getReader();

        // Enumeration<String> headers = request.getHeaderNames();
        // String boundary = "";
        // while (headers.hasMoreElements())
        // {
        // String header = (String) headers.nextElement();
        // String hvalue = request.getHeader(header);
        // out.println(header + ": " + hvalue);
        // if (header.contains("boundaries"))
        // {
        // boundary = hvalue;
        // }
        // }
        // out.println("-B " + boundary);

        // String ct = request.getContentType();
        // String boundaryTag = "boundary=";
        // out.println("bound=" + ct.substring(ct.indexOf(boundaryTag) +
        // boundaryTag.length(), ct.length()));
        out.println("<h2>---------</h2>");

        String line;
        boolean foundFilenameMarker = false;
        boolean foundUploadMarker = false;
        boolean foundNewLine = false;
        String newFilename = null;
        while ((line = reader.readLine()) != null)
        {
            out.println("|-|" + line); // --> Ausgabe der Datei
            // System.out.println(line);
            if (foundUploadMarker && foundNewLine)
            {
                break;
            }
            else
            {

                if (line.contains("name=" + FILENAME))
                {
                    foundFilenameMarker = true;
                }
                if (line.contains("\n\n"))
                {
                    foundNewLine = true;
                }
                if (foundNewLine && !line.isEmpty())
                {
                    newFilename = line;
                    foundFilenameMarker = false;
                    foundNewLine = false;
                }
                if (line.contains("name=" + UPLOAD))
                {
                    foundUploadMarker = true;
                }

            }
        }

        if (foundUploadMarker)
        {
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }
            files.add(fileName, buffer);

        }

        out.println("</pre>");

        out.println("<p>End of reader</p>");
        out.println("</body>\n</html>");

    }

}
