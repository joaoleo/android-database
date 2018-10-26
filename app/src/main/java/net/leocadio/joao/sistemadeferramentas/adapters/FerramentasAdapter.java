package net.leocadio.joao.sistemadeferramentas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import net.leocadio.joao.sistemadeferramentas.R;
import net.leocadio.joao.sistemadeferramentas.models.Ferramenta;

import java.util.ArrayList;

public class FerramentasAdapter extends ArrayAdapter<Ferramenta> {

    private ArrayList<Ferramenta> ferramenta;
    private Context context;

    public FerramentasAdapter(Context c, ArrayList<Ferramenta> objetos) {
        super(c, 0, objetos);

        this.context = c;
        this.ferramenta = objetos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (ferramenta != null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.lista_ferramentas, parent, false);

            TextView textFerramenta = (TextView) view.findViewById(R.id.textViewFerramenta);
            TextView textFabricante = (TextView) view.findViewById(R.id.textViewFabricante);
            TextView textReferencia = (TextView) view.findViewById(R.id.textViewReferencia);

            Ferramenta tools = ferramenta.get(position);
            textFerramenta.setText(tools.getFerramenta());
            textFabricante.setText(tools.getFabricante());
            textReferencia.setText(tools.getReferencia());
        }
        return view;
    }
}
