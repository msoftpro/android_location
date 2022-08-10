package pro.msoft.coordonatele;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AddAbonatActivity extends AppCompatActivity {


    TextView textShowAddress;
    private GoogleMap mMap;
    LatLng latLng;
    double latitude = 17.385044;
    double longitude = 78.486671;
    float zoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_abonat);

    }

//    public String getAddress(double latitude, double longitude) {
//        StringBuilder result = new StringBuilder();
//        try {
//
//            System.out.println("get address");
//            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
//            if (addresses.size() > 0) {
//                System.out.println("size====" + addresses.size());
//                Address address = addresses.get(0);
//
//                for (int i = 0; i <= addresses.get(0).getMaxAddressLineIndex(); i++) {
//                    if (i == addresses.get(0).getMaxAddressLineIndex()) {
//                        result.append(addresses.get(0).getAddressLine(i));
//                    } else {
//                        result.append(addresses.get(0).getAddressLine(i) + ",");
//                    }
//                }
//                System.out.println("ad==" + address);
//                System.out.println("result---" + result.toString());
//
//                autoComplete_location.setText(result.toString()); // Here is you AutoCompleteTextView where you want to set your string address (You can remove it if you not need it)
//            }
//        } catch (IOException e) {
//            Log.e("tag", e.getMessage());
//        }
//
//        return result.toString();
//    }

}