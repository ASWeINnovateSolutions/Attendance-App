package developer.aswin.com.nitc;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class LocationListActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn[]=new Button[46];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        int i=0;
        btn[i]=(Button) findViewById(R.id.button1);
        btn[i+1]=(Button) findViewById(R.id.button2);
        btn[i+2]=(Button) findViewById(R.id.button3);
        btn[i+3]=(Button) findViewById(R.id.button4);
        btn[i+4]=(Button) findViewById(R.id.button5);
        btn[i+5]=(Button) findViewById(R.id.button6);
        btn[i+6]=(Button) findViewById(R.id.button7);
        btn[i+7]=(Button) findViewById(R.id.button8);
        btn[i+8]=(Button) findViewById(R.id.button9);
        btn[i+9]=(Button) findViewById(R.id.button10);
        btn[i+10]=(Button) findViewById(R.id.button11);
        btn[i+11]=(Button) findViewById(R.id.button12);
        btn[i+12]=(Button) findViewById(R.id.button13);
        btn[i+13]=(Button) findViewById(R.id.button14);
        btn[i+14]=(Button) findViewById(R.id.button15);
        btn[i+15]=(Button) findViewById(R.id.button16);
        btn[i+16]=(Button) findViewById(R.id.button17);
        btn[i+17]=(Button) findViewById(R.id.button18);
        btn[i+18]=(Button) findViewById(R.id.button19);
        btn[i+19]=(Button) findViewById(R.id.button20);
        btn[i+20]=(Button) findViewById(R.id.button21);
        btn[i+21]=(Button) findViewById(R.id.button22);
        btn[i+22]=(Button) findViewById(R.id.button23);
        btn[i+23]=(Button) findViewById(R.id.button24);
        btn[i+24]=(Button) findViewById(R.id.button25);
        btn[i+25]=(Button) findViewById(R.id.button26);
        btn[i+26]=(Button) findViewById(R.id.button27);
        btn[i+27]=(Button) findViewById(R.id.button28);
        btn[i+28]=(Button) findViewById(R.id.button29);
        btn[i+29]=(Button) findViewById(R.id.button30);
        btn[i+30]=(Button) findViewById(R.id.button31);
        btn[i+31]=(Button) findViewById(R.id.button32);
        btn[i+32]=(Button) findViewById(R.id.button33);
        btn[i+33]=(Button) findViewById(R.id.button34);
        btn[i+34]=(Button) findViewById(R.id.button35);
        btn[i+35]=(Button) findViewById(R.id.button36);
        btn[i+36]=(Button) findViewById(R.id.button37);
        btn[i+37]=(Button) findViewById(R.id.button38);
        btn[i+38]=(Button) findViewById(R.id.button39);
        btn[i+39]=(Button) findViewById(R.id.button40);
        btn[i+40]=(Button) findViewById(R.id.button41);
        btn[i+41]=(Button) findViewById(R.id.button42);
        btn[i+42]=(Button) findViewById(R.id.button43);
        btn[i+43]=(Button) findViewById(R.id.button44);
        btn[i+44]=(Button) findViewById(R.id.button45);
        btn[i+45]=(Button) findViewById(R.id.button46);

        for(i=0;i<46;i++) {
            btn[i].setOnClickListener(this);
        }

    }

    public void open(String uri)
    {
        Intent intent=new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
        startActivity(intent);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        { case R.id.button1: open("http://maps.google.com/maps?daddr=11.319634, 75.931191");
            break;
            case R.id.button2: open("http://maps.google.com/maps?daddr=11.320053, 75.931317");
                break;
            case R.id.button3: open("http://maps.google.com/maps?daddr=11.319907, 75.931888");
                break;
            case R.id.button4: open("http://maps.google.com/maps?daddr=11.319813, 75.932258");
                break;
            case R.id.button5: open("http://maps.google.com/maps?daddr=11.320102, 75.932666");
                break;
            case R.id.button6: open("http://maps.google.com/maps?daddr=11.320656, 75.932246");
                break;
            case R.id.button7: open("http://maps.google.com/maps?daddr=11.321045, 75.931944");
                break;
            case R.id.button8: open("http://maps.google.com/maps?daddr=11.321764, 75.932871");
                break;
            case R.id.button9: open("http://maps.google.com/maps?daddr=11.322211, 75.933267");
                break;
            case R.id.button10: open("http://maps.google.com/maps?daddr=11.322594, 75.933752");
                break;
            case R.id.button11: open("http://maps.google.com/maps?daddr=11.322878, 75.934159");
                break;
            case R.id.button12: open("http://maps.google.com/maps?daddr=11.322504, 75.934784");
                break;
            case R.id.button13: open("http://maps.google.com/maps?daddr=11.322161, 75.934663");
                break;
            case R.id.button14: open("http://maps.google.com/maps?daddr=11.321827, 75.935029");
                break;
            case R.id.button15: open("http://maps.google.com/maps?daddr=11.321347, 75.933361");
                break;
            case R.id.button16: open("http://maps.google.com/maps?daddr=11.32089, 75.934078");
                break;
            case R.id.button17: open("http://maps.google.com/maps?daddr=11.320257, 75.933558");
                break;
            case R.id.button18: open("http://maps.google.com/maps?daddr=11.320021, 75.934998");
                break;
            case R.id.button19: open("http://maps.google.com/maps?daddr=11.320535, 75.934948");
                break;
            case R.id.button20: open("http://maps.google.com/maps?daddr=11.320334, 75.935862");
                break;
            case R.id.button21: open("http://maps.google.com/maps?daddr=11.319898, 75.935852");
                break;
            case R.id.button22: open("http://maps.google.com/maps?daddr=11.320774, 75.936628");
                break;
            case R.id.button23: open("http://maps.google.com/maps?daddr=11.320774, 75.936628");
                break;
            case R.id.button24: open("http://maps.google.com/maps?daddr=11.319889, 75.937667");
                break;
            case R.id.button25: open("http://maps.google.com/maps?daddr=11.320958, 75.937599");
                break;
            case R.id.button26: open("http://maps.google.com/maps?daddr=11.319644, 75.938504");
                break;
            case R.id.button27: open("http://maps.google.com/maps?daddr=11.321627, 75.937148");
                break;
            case R.id.button28: open("http://maps.google.com/maps?daddr=11.321525, 75.935977");
                break;
            case R.id.button29: open("http://maps.google.com/maps?daddr=11.321142, 75.935517");
                break;
            case R.id.button30: open("http://maps.google.com/maps?daddr=11.321094, 75.934745");
                break;
            case R.id.button31: open("http://maps.google.com/maps?daddr=11.321261, 75.935112");
                break;
            case R.id.button32: open("http://maps.google.com/maps?daddr=11.322455, 75.935862");
                break;
            case R.id.button33: open("http://maps.google.com/maps?daddr=11.321597, 75.933619");
                break;
            case R.id.button34: open("http://maps.google.com/maps?daddr=11.319111, 75.936131");
                break;
            case R.id.button35: open("http://maps.google.com/maps?daddr=11.317375, 75.93592");
                break;
            case R.id.button36: open("http://maps.google.com/maps?daddr=11.317651, 75.930932");
                break;
            case R.id.button37: open("http://maps.google.com/maps?daddr=11.315741, 75.934817");
                break;
            case R.id.button38: open("http://maps.google.com/maps?daddr=11.315069, 75.928456");
                break;
            case R.id.button39: open("http://maps.google.com/maps?daddr=11.322647, 75.93788");
                break;
            case R.id.button40: open("http://maps.google.com/maps?daddr=11.323843, 75.938317");
                break;
            case R.id.button41: open("http://maps.google.com/maps?daddr=11.323442, 75.938205");
                break;
            case R.id.button42: open("http://maps.google.com/maps?daddr=11.323526, 75.938587");
                break;
            case R.id.button43: open("http://maps.google.com/maps?daddr=11.313823, 75.931811");
                break;
            case R.id.button44: open("http://maps.google.com/maps?daddr=11.314898, 75.932585");
                break;
            case R.id.button45: open("http://maps.google.com/maps?daddr=11.318090, 75.933640");
                break;
            case R.id.button46: open("http://maps.google.com/maps?daddr=11.32412, 75.937252");
                break;

        }
    }

}

