package is.currency.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/*
* Currency created by IsCoelho
* @author Coelho <robertcoelho@live.com>
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*/
public class ConfigUtils {

	public static void extract(File directory, String... names) {
        for(String name : names) {
            File actual = new File(directory, name);
            if(actual.exists()) {
                continue;
            }
            InputStream input = ConfigUtils.class.getResourceAsStream("/default/" + name);
            if(input == null) {
                continue;
            }
            FileOutputStream output = null;
            try {
                output = new FileOutputStream(actual);
                byte[] buf = new byte[8192];
                int length = 0;
                while ((length = input.read(buf)) > 0) {
                    output.write(buf, 0, length);
                }
                System.out.println("Default setup file written: " + actual.getAbsolutePath());
            } catch (Exception exception) {
            } finally {
                try {
                	if (input != null) {
                		input.close();
                	}
                } catch (Exception exception) { }

                try { 
                	if (output != null) {
                		output.close();
                	}
                } catch (Exception exception) { }
            }
        }
    }

	
}
