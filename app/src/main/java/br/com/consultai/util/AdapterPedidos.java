package br.com.consultai.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import br.com.consultai.R;
import br.com.consultai.fragments.PedidosFragment;
import br.com.consultai.model.Pedidos;

/**
 * Created by leonardo.ribeiro on 27/03/2018.
 */

public class AdapterPedidos extends BaseAdapter{
    private final Pedidos pedido;
    private final Context context;

    public AdapterPedidos(Context context, Pedidos pedido) {
        this.context = context;
    this.pedido = pedido;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.list_pedidos, null);

        return view;
    }
}
